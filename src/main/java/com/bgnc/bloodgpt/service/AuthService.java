package com.bgnc.bloodgpt.service;

import com.bgnc.bloodgpt.dto.request.AuthRequest;
import com.bgnc.bloodgpt.dto.request.RegisterRequest;
import com.bgnc.bloodgpt.dto.response.AuthResponse;

public interface AuthService {

    void register(RegisterRequest request);

    AuthResponse authenticate(AuthRequest request);

}
