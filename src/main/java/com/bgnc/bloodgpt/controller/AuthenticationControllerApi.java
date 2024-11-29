package com.bgnc.bloodgpt.controller;

import com.bgnc.bloodgpt.dto.request.AuthRequest;
import com.bgnc.bloodgpt.dto.request.RegisterRequest;
import com.bgnc.bloodgpt.dto.response.AuthResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.bgnc.bloodgpt.utils.AuthenticationControllerApis.*;

@Tag(name = "Authentication", description = "Endpoints for user registration and authentication.")
@RequestMapping(AUTH_CONTROLLER)
public interface AuthenticationControllerApi {

    /**
     * Registers a new user in the system.
     *
     * @param request The registration request containing user details.
     * @return A success message upon successful registration.
     */
    @PostMapping(REGISTER)
    @Operation(summary = "Register User", description = "Registers a new user with first name, last name, TC number, and password.")
    ResponseEntity<String> register(@Valid @RequestBody RegisterRequest request);

    /**
     * Authenticates a user and returns an access token.
     *
     * @param request The authentication request containing TC number and password.
     * @return An authentication response with an access token.
     */
    @PostMapping(LOGIN)
    @Operation(summary = "Login User", description = "Authenticates a user with TC number and password and returns an access token.")
    ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest request);
}