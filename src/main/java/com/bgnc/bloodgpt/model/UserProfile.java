package com.bgnc.bloodgpt.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;


@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfile extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Min(value = 0, message = "Age cannot be negative")
    private Integer age; // Yaş

    @Min(value = 0, message = "Height cannot be negative")
    private Double height; // Boy (metre cinsinden)

    @Min(value = 0, message = "Weight cannot be negative")
    private Double weight; // Kilo (kilogram cinsinden)

    @Email(message = "Invalid email address")
    private String email; // E-posta

    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Invalid phone number")
    private String phoneNumber; // Cep telefonu numarası

    @Transient
    private Double bmi; // Vücut kitle indeksi (hesaplanacak)

    @PreUpdate
    public void calculateBMI() {
        if (height != null && weight != null && height > 0) {
            this.bmi = weight / (height * height);
        } else {
            this.bmi = null;
        }
    }
}