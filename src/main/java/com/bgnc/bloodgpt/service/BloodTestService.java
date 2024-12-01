package com.bgnc.bloodgpt.service;

import com.bgnc.bloodgpt.dto.response.BloodTestResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BloodTestService {

    /**
     * Upload a blood test PDF file and associate it with a user.
     *
     * @param file       the PDF file to upload
     * @param tcNumber   the TC number of the user
     * @param doctorId   the ID of the doctor who requested the test (optional)
     */
    void uploadBloodTest(MultipartFile file, String tcNumber, Long doctorId);

    /**
     * Get all blood tests of a specific user by their TC number.
     *
     * @param tcNumber the TC number of the user
     * @return a list of BloodTestResponse DTOs
     */
    List<BloodTestResponse> getUserBloodTests(String tcNumber);
}