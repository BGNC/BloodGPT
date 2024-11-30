package com.bgnc.bloodgpt.dto.request;



import com.bgnc.bloodgpt.enums.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class RegisterRequest {

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotBlank(message = "TC number is required")
    @Pattern(regexp = "^[0-9]{11}$", message = "TCKN must be exactly 11 digits")
    private String tcNumber;

    @NotBlank(message = "Password is required")
    private String password;

    // Role isteğe bağlı (varsayılan olarak PATIENT atanır)
    private Role role = Role.PATIENT; // Varsayılan rol

    // Doktorlar için isteğe bağlı uzmanlık alanı
    private String specialization;
}
