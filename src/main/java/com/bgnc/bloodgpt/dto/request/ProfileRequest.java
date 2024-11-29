package com.bgnc.bloodgpt.dto.request;


import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ProfileRequest {

    @Min(value = 0, message = "Age cannot be negative")
    private Integer age;

    @Min(value = 0, message = "Height cannot be negative")
    private Double height;

    @Min(value = 0, message = "Weight cannot be negative")
    private Double weight;

    @Email(message = "Invalid email address")
    private String email;

    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Invalid phone number")
    private String phoneNumber;
}
