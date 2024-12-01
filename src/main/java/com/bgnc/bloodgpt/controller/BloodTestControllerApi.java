package com.bgnc.bloodgpt.controller;

import com.bgnc.bloodgpt.dto.response.BloodTestResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.bgnc.bloodgpt.utils.BloodTestControllerApis.*;

@Tag(name = "Blood Test Management", description = "Endpoints for managing blood tests.")
@RequestMapping(BLOOD_TEST_CONTROLLER)
public interface BloodTestControllerApi {

    @PostMapping(UPLOAD)
    @Operation(summary = "Upload Blood Test", description = "Uploads a PDF file containing blood test results.")
    ResponseEntity<String> uploadBloodTest(
            @RequestParam(FILE) MultipartFile file,
            @RequestParam("tcNumber") String tcNumber,
            @RequestParam(value = "doctorId", required = false) Long doctorId);

    @GetMapping(USER_TESTS)
    @Operation(summary = "Get User Blood Tests", description = "Fetches all blood tests of a specific user by TC number.")
    ResponseEntity<List<BloodTestResponse>> getUserBloodTests(@PathVariable String tcNumber);
}