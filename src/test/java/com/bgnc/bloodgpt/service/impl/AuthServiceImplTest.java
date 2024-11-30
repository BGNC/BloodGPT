package com.bgnc.bloodgpt.service.impl;

import com.bgnc.bloodgpt.dto.request.AuthRequest;
import com.bgnc.bloodgpt.dto.request.RegisterRequest;
import com.bgnc.bloodgpt.dto.response.AuthResponse;
import com.bgnc.bloodgpt.enums.Role;
import com.bgnc.bloodgpt.exception.BaseException;
import com.bgnc.bloodgpt.jwt.JWTService;
import com.bgnc.bloodgpt.model.User;
import com.bgnc.bloodgpt.model.UserProfile;
import com.bgnc.bloodgpt.repository.UserProfileRepository;
import com.bgnc.bloodgpt.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class AuthServiceImplTest {

    @InjectMocks
    private AuthServiceImpl authService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserProfileRepository userProfileRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JWTService jwtService;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void testRegisterSuccess() {
        // Arrange
        RegisterRequest request = new RegisterRequest();
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setTcNumber("12345678901");
        request.setPassword("password");
        request.setRole(Role.PATIENT);

        when(userRepository.findByTcNumber(request.getTcNumber())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(request.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        authService.register(request);

        // Assert
        verify(userRepository, times(1)).save(any(User.class));
        verify(userProfileRepository, times(1)).save(any(UserProfile.class));
    }

    @Test
    void testRegisterFailsWhenTcNumberExists() {
        // Arrange
        RegisterRequest request = new RegisterRequest();
        request.setTcNumber("12345678901");

        when(userRepository.findByTcNumber(request.getTcNumber())).thenReturn(Optional.of(new User()));

        // Act & Assert
        BaseException exception = assertThrows(BaseException.class, () -> authService.register(request));
        assertEquals("TC number already registered", exception.getMessage());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testAuthenticateSuccess() {
        // Arrange
        AuthRequest request = new AuthRequest();
        request.setTcNumber("12345678901");
        request.setPassword("password");

        User user = User.builder()
                .tcNumber("12345678901")
                .password("encodedPassword")
                .role(Role.PATIENT)
                .build();

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(request.getTcNumber(), request.getPassword());

        when(authenticationManager.authenticate(authenticationToken))
                .thenReturn(authenticationToken);
        when(jwtService.generateToken(any(User.class))).thenReturn("mockToken");

        // Act
        AuthResponse response = authService.authenticate(request);

        // Assert
        assertNotNull(response);
        assertEquals("mockToken", response.getAccessToken());
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtService, times(1)).generateToken(any(User.class));
    }

    @Test
    void testAuthenticateFails() {
        // Arrange
        AuthRequest request = new AuthRequest();
        request.setTcNumber("12345678901");
        request.setPassword("password");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new RuntimeException("Authentication failed"));

        // Act & Assert
        BaseException exception = assertThrows(BaseException.class, () -> authService.authenticate(request));
        assertEquals("Authentication failed", exception.getMessage());
        verify(jwtService, never()).generateToken(any(User.class));
    }
}