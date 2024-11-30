package com.bgnc.bloodgpt.controller.impl;

import com.bgnc.bloodgpt.controller.DoctorControllerApi;
import com.bgnc.bloodgpt.dto.response.DoctorResponse;
import com.bgnc.bloodgpt.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DoctorController implements DoctorControllerApi {

    private final DoctorService doctorService;

    @Override
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<DoctorResponse> getDoctorDetails(String tcNumber) {
        DoctorResponse doctorResponse = doctorService.getDoctorByTcNumber(tcNumber);
        return ResponseEntity.ok(doctorResponse);
    }

    @Override
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<String> updateDoctorDetails(String tcNumber, String specialization, Long hospitalId) {
        doctorService.updateDoctorDetails(tcNumber, specialization, hospitalId);
        return ResponseEntity.ok("Doctor details updated successfully");
    }
}