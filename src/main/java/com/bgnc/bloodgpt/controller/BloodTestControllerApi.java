package com.bgnc.bloodgpt.controller;



import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import static com.bgnc.bloodgpt.config.RestApis.BloodTestControllerApis.*;

@Tag(name = "Blood Test Analysis", description = "Endpoints for blood test data parsing and analysis.")
@RequestMapping(BLOOD_TEST_CONTROLLER)
public interface BloodTestControllerApi {

    /**
     * Endpoint to analyze blood test results from a PDF file.
     *
     * @param file The PDF file containing blood test results.
     * @return Analysis of the blood test data as a String.
     */
    @PostMapping(ANALYZE)
    @Operation(summary = "Analyze Blood Test", description = "Parses a PDF file with blood test data and analyzes it using ChatGPT.")
    ResponseEntity<String> analyzeBloodTest(@RequestParam(FILE) MultipartFile file);
}
