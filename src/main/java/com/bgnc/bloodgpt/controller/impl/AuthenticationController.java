package com.bgnc.bloodgpt.controller.impl;

import com.bgnc.bloodgpt.controller.AuthenticationControllerApi;
import com.bgnc.bloodgpt.dto.request.AuthRequest;
import com.bgnc.bloodgpt.dto.request.RegisterRequest;
import com.bgnc.bloodgpt.dto.response.AuthResponse;
import com.bgnc.bloodgpt.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthenticationController implements AuthenticationControllerApi {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    private final AuthService authService;

    @Override
    public ResponseEntity<String> register(RegisterRequest request) {
        logger.info("Register request received for TC Number: {}", request.getTcNumber());
        try {
            authService.register(request);
            logger.info("User registered successfully for TC Number: {}", request.getTcNumber());
            return ResponseEntity.ok("User registered successfully");
        } catch (Exception e) {
            logger.error("Error occurred while registering user with TC Number: {}. Error: {}", request.getTcNumber(), e.getMessage());
            throw e; // Hatanın üst katmana iletilmesi
        }
    }

    @Override
    public ResponseEntity<AuthResponse> login(AuthRequest request) {
        logger.info("Login request received for TC Number: {}", request.getTcNumber());
        try {
            AuthResponse response = authService.authenticate(request);
            logger.info("Login successful for TC Number: {}", request.getTcNumber());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error occurred while logging in user with TC Number: {}. Error: {}", request.getTcNumber(), e.getMessage());
            throw e; // Hatanın üst katmana iletilmesi
        }
    }
}