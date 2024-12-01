package com.bgnc.bloodgpt.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParsedBloodTestData {

    @NotBlank(message = "Patient name must not be blank")
    private String patientName;

    @NotBlank(message = "Date must not be blank")
    private String date;

    @NotNull(message = "Content must not be null")
    @NotBlank(message = "Content must not be blank")
    private String content;
}