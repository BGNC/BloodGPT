package com.bgnc.bloodgpt.controller;

import com.bgnc.bloodgpt.dto.response.DoctorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.bgnc.bloodgpt.utils.DoctorControllerApis.*;

@Tag(name = "Doctor Management", description = "Endpoints for managing doctor details.")
@RequestMapping(DOCTOR_CONTROLLER)
public interface DoctorControllerApi {

    @GetMapping(DETAILS)
    @Operation(summary = "Get Doctor Details", description = "Fetch doctor details by TC number.")
    ResponseEntity<DoctorResponse> getDoctorDetails(@PathVariable String tcNumber);

    @PutMapping(UPDATE_DETAILS)
    @Operation(summary = "Update Doctor Details", description = "Update specialization and hospital for a doctor.")
    ResponseEntity<String> updateDoctorDetails(
            @PathVariable String tcNumber,
            @RequestParam String specialization,
            @RequestParam Long hospitalId);


}