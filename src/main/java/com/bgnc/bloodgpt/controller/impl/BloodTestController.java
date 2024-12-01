package com.bgnc.bloodgpt.controller.impl;

import com.bgnc.bloodgpt.controller.BloodTestControllerApi;
import com.bgnc.bloodgpt.dto.response.BloodTestResponse;
import com.bgnc.bloodgpt.service.BloodTestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BloodTestController implements BloodTestControllerApi {

    private final BloodTestService bloodTestService;

    @PreAuthorize("hasAnyRole('PATIENT', 'DOCTOR')")
    @Override
    public ResponseEntity<String> uploadBloodTest(MultipartFile file, String tcNumber, Long doctorId) {
        bloodTestService.uploadBloodTest(file, tcNumber, doctorId);
        return ResponseEntity.ok("Blood test uploaded successfully.");
    }

    @PreAuthorize("hasAnyRole('PATIENT','DOCTOR')")
    @Override
    public ResponseEntity<List<BloodTestResponse>> getUserBloodTests(String tcNumber) {
        return ResponseEntity.ok(bloodTestService.getUserBloodTests(tcNumber));
    }
}