package com.bgnc.bloodgpt.service.impl;

import com.bgnc.bloodgpt.dto.request.AuthRequest;
import com.bgnc.bloodgpt.dto.request.RegisterRequest;
import com.bgnc.bloodgpt.dto.response.AuthResponse;
import com.bgnc.bloodgpt.enums.MessageType;
import com.bgnc.bloodgpt.enums.Role;
import com.bgnc.bloodgpt.exception.BaseException;
import com.bgnc.bloodgpt.exception.ErrorMessage;
import com.bgnc.bloodgpt.jwt.JWTService;
import com.bgnc.bloodgpt.model.User;
import com.bgnc.bloodgpt.model.UserProfile;
import com.bgnc.bloodgpt.repository.UserProfileRepository;
import com.bgnc.bloodgpt.repository.UserRepository;
import com.bgnc.bloodgpt.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    /**
     * Register
     */
    @Override

    public void register(RegisterRequest request) {
        logger.info("Registering user with TC Number: {}", request.getTcNumber());

        // Aynı TC numarasıyla kayıtlı kullanıcı kontrolü
        if (userRepository.findByTcNumber(request.getTcNumber()).isPresent()) {
            logger.warn("Registration failed. TC Number: {} is already registered", request.getTcNumber());
            throw new BaseException(new ErrorMessage<>(MessageType.DUPLICATE_TC_NUMBER, "TC number already registered"));
        }

        // Kullanıcı oluşturuluyor
        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .tcNumber(request.getTcNumber())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.PATIENT) // Varsayılan rol PATIENT
                .build();

        userRepository.save(user);
        logger.info("User registered successfully with TC Number: {}", request.getTcNumber());

        // Kullanıcı için boş bir profil oluşturuluyor
        UserProfile profile = UserProfile.builder()
                .user(user)
                .build();

        userProfileRepository.save(profile);
        logger.info("UserProfile created for TC Number: {}", request.getTcNumber());
    }

    /**
     * Authentication
     */
    public AuthResponse authenticate(AuthRequest request) {
        logger.info("Authenticating user with TC Number: {}", request.getTcNumber());

        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getTcNumber(),
                            request.getPassword()
                    )
            );
        } catch (Exception ex) {
            logger.error("Authentication failed for TC Number: {}. Reason: {}", request.getTcNumber(), ex.getMessage());
            throw new BaseException(new ErrorMessage<>(MessageType.AUTHENTICATION_FAILED_TC_NUMBER, ex.getMessage()));
        }

        User user = (User) authentication.getPrincipal();
        String token = jwtService.generateToken(user);

        logger.info("Authentication successful for TC Number: {}", request.getTcNumber());
        return new AuthResponse(token);
    }
}