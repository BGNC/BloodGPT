package com.bgnc.bloodgpt.controller.impl;

import com.bgnc.bloodgpt.controller.BloodTestControllerApi;
import com.bgnc.bloodgpt.dto.ParsedBloodTestData;
import com.bgnc.bloodgpt.service.BloodTestAnalysisService;
import com.bgnc.bloodgpt.service.FileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
public class BloodTestController implements BloodTestControllerApi {

    private final BloodTestAnalysisService bloodTestAnalysisService;
    private final FileService fileService;

    @ResponseStatus(OK)
    @Override
    public ResponseEntity<String> analyzeBloodTest(MultipartFile file) {

        @Valid ParsedBloodTestData parsedData = fileService.uploadAndParsePDF(file);

        String analysis = bloodTestAnalysisService.analyzeBloodTest(parsedData);

        return ResponseEntity.ok(analysis);
    }
}