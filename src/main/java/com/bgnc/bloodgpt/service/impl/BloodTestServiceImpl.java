package com.bgnc.bloodgpt.service.impl;

import com.bgnc.bloodgpt.dto.request.ParsedBloodTestData;
import com.bgnc.bloodgpt.dto.response.BloodTestResponse;
import com.bgnc.bloodgpt.enums.MessageType;
import com.bgnc.bloodgpt.exception.BaseException;
import com.bgnc.bloodgpt.exception.ErrorMessage;
import com.bgnc.bloodgpt.model.BloodTest;
import com.bgnc.bloodgpt.model.Doctor;
import com.bgnc.bloodgpt.model.User;
import com.bgnc.bloodgpt.repository.BloodTestRepository;
import com.bgnc.bloodgpt.repository.DoctorRepository;
import com.bgnc.bloodgpt.repository.UserRepository;
import com.bgnc.bloodgpt.service.BloodTestAnalysisService;
import com.bgnc.bloodgpt.service.BloodTestService;
import com.bgnc.bloodgpt.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BloodTestServiceImpl implements BloodTestService {

    private final BloodTestRepository bloodTestRepository;
    private final UserRepository userRepository;
    private final DoctorRepository doctorRepository;
    private final FileService fileService;
    private final BloodTestAnalysisService bloodTestAnalysisService;

    @Override
    public void uploadBloodTest(MultipartFile file, String tcNumber, Long doctorId) {
        log.info("Starting blood test upload process for TC: {}", tcNumber);

        User user = getUserByTcNumber(tcNumber);
        Doctor doctor = getDoctorByIdIfProvided(doctorId);

        String filePath = saveUploadedFile(file);
        ParsedBloodTestData parsedData = parseUploadedPDF(file);
        String aiComment = analyzeBloodTestData(parsedData);

        saveBloodTest(user, doctor, filePath, aiComment);

        log.info("Successfully uploaded blood test for TC: {}", tcNumber);
    }

    @Override
    public List<BloodTestResponse> getUserBloodTests(String tcNumber) {
        log.info("Fetching blood tests for user with TC: {}", tcNumber);

        User user = getUserByTcNumber(tcNumber);

        return bloodTestRepository.findByUser_Id(user.getId()).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }



    private User getUserByTcNumber(String tcNumber) {
        return userRepository.findByTcNumber(tcNumber)
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage<>(MessageType.PROFILE_NOT_FOUND, "User not found with TC: " + tcNumber)));
    }

    private Doctor getDoctorByIdIfProvided(Long doctorId) {
        if (doctorId == null) return null;

        return doctorRepository.findById(doctorId)
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage<>(MessageType.DOCTOR_NOT_FOUND, "Doctor not found with ID: " + doctorId)));
    }

    private String saveUploadedFile(MultipartFile file) {
        log.debug("Saving uploaded file...");
        return fileService.uploadFile(file);
    }

    private ParsedBloodTestData parseUploadedPDF(MultipartFile file) {
        log.debug("Parsing uploaded PDF...");
        return fileService.uploadAndParsePDF(file);
    }

    private String analyzeBloodTestData(ParsedBloodTestData parsedData) {
        log.debug("Analyzing blood test data...");
        return bloodTestAnalysisService.analyzeBloodTest(parsedData);
    }

    private void saveBloodTest(User user, Doctor doctor, String filePath, String aiComment) {
        log.debug("Saving blood test data to the database...");
        BloodTest bloodTest = BloodTest.builder()
                .user(user)
                .requestedBy(doctor)
                .filePath(filePath)
                .aiComment(aiComment)
                .uploadTime(LocalDateTime.now())
                .build();

        bloodTestRepository.save(bloodTest);
    }

    private BloodTestResponse mapToResponse(BloodTest bloodTest) {
        return BloodTestResponse.builder()
                .id(bloodTest.getId())
                .filePath(bloodTest.getFilePath())
                .aiComment(bloodTest.getAiComment())
                .doctorComment(bloodTest.getDoctorComment())
                .uploadTime(bloodTest.getUploadTime())
                .requestedBy(bloodTest.getRequestedBy() != null
                        ? bloodTest.getRequestedBy().getUser().getFirstName() + " " + bloodTest.getRequestedBy().getUser().getLastName()
                        : null)
                .build();
    }
}