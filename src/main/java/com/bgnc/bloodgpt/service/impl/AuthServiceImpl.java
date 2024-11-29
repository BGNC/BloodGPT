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
import com.bgnc.bloodgpt.repository.UserRepository;
import com.bgnc.bloodgpt.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    /**
     * Kullanıcı kaydını gerçekleştiren metot.
     */
    public void register(RegisterRequest request) {
        if (userRepository.findByTcNumber(request.getTcNumber()).isPresent()) {
            throw new BaseException(new ErrorMessage<>(MessageType.USERNAME_ALREADY_EXISTS, request.getFirstName()));
        }

        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .tcNumber(request.getTcNumber())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.PATIENT)
                .build();

        userRepository.save(user);
    }

    public AuthResponse authenticate(AuthRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getTcNumber(),
                        request.getPassword()
                )
        );

        User user = (User) authentication.getPrincipal();
        String token = jwtService.generateToken(user);

        return new AuthResponse(token);
    }
}