package com.bgnc.bloodgpt.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class AuthRequest {

    @NotBlank(message = "TC number is required")
    @Pattern(regexp = "^[0-9]{11}$", message = "TCKN must be exactly 11 digits")
    private String tcNumber; // TC kimlik no

    @NotBlank(message = "Password is required")
    private String password; // Şifre
}