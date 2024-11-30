package com.bgnc.bloodgpt.service.impl;

import com.bgnc.bloodgpt.dto.response.DoctorResponse;
import com.bgnc.bloodgpt.enums.MessageType;
import com.bgnc.bloodgpt.exception.BaseException;
import com.bgnc.bloodgpt.exception.ErrorMessage;
import com.bgnc.bloodgpt.model.Doctor;
import com.bgnc.bloodgpt.model.Hospital;
import com.bgnc.bloodgpt.model.User;
import com.bgnc.bloodgpt.repository.DoctorRepository;
import com.bgnc.bloodgpt.repository.HospitalRepository;
import com.bgnc.bloodgpt.service.DoctorService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class DoctorServiceImpl implements DoctorService {

    private static final Logger logger = LoggerFactory.getLogger(DoctorServiceImpl.class);
    private final DoctorRepository doctorRepository;
    private final HospitalRepository hospitalRepository;

    @Override
    public void createDoctor(User user) {
        Doctor doctor = Doctor.builder()
                .user(user)
                .build();

        doctorRepository.save(doctor);
        logger.info("Doctor profile created for TC Number: {}", user.getTcNumber());
    }

    @Override
    public DoctorResponse getDoctorByTcNumber(String tcNumber) {
        logger.info("Fetching doctor details for TC Number: {}", tcNumber);

        Doctor doctor = doctorRepository.findByUser_TcNumber(tcNumber)
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage<>(MessageType.DOCTOR_NOT_FOUND, "Doctor not found with TC Number: " + tcNumber)
                ));

        return mapToDoctorResponse(doctor);
    }

    @Override
    @Transactional
    public void updateDoctorDetails(String tcNumber, String specialization, Long hospitalId) {
        logger.info("Updating doctor details for TC Number: {}", tcNumber);

        Doctor doctor = doctorRepository.findByUser_TcNumber(tcNumber)
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage<>(MessageType.DOCTOR_NOT_FOUND, "Doctor not found with TC Number: " + tcNumber)
                ));

        Hospital hospital = hospitalRepository.findById(hospitalId)
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage<>(MessageType.HOSPITAL_NOT_FOUND, "Hospital not found with ID: " + hospitalId)
                ));

        doctor.setSpecialization(specialization);
        doctor.setHospital(hospital);

        doctorRepository.save(doctor);

        logger.info("Doctor details updated successfully for TC Number: {}", tcNumber);
    }

    /**
     * Maps a Doctor entity to a DoctorResponse DTO.
     *
     * @param doctor The Doctor entity.
     * @return The mapped DoctorResponse DTO.
     */
    private DoctorResponse mapToDoctorResponse(Doctor doctor) {
        return DoctorResponse.builder()
                .firstName(doctor.getUser().getFirstName())
                .lastName(doctor.getUser().getLastName())
                .tcNumber(doctor.getUser().getTcNumber())
                .specialization(doctor.getSpecialization())
                .hospitalName(doctor.getHospital().getName())
                .districtName(doctor.getHospital().getDistrict().getName())
                .cityName(doctor.getHospital().getDistrict().getCity().getName())
                .build();
    }
}
