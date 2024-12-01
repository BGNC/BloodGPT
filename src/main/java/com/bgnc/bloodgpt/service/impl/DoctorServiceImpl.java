package com.bgnc.bloodgpt.service.impl;

import com.bgnc.bloodgpt.dto.response.DoctorResponse;
import com.bgnc.bloodgpt.exception.BaseException;
import com.bgnc.bloodgpt.exception.ErrorMessage;
import com.bgnc.bloodgpt.enums.MessageType;
import com.bgnc.bloodgpt.model.Doctor;
import com.bgnc.bloodgpt.model.Hospital;
import com.bgnc.bloodgpt.model.User;
import com.bgnc.bloodgpt.repository.DoctorRepository;
import com.bgnc.bloodgpt.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private static final Logger logger = LoggerFactory.getLogger(DoctorServiceImpl.class);

    private final DoctorRepository doctorRepository;

    /**
     * Kullanıcıyı doktor olarak kaydeder.
     * @param user Kullanıcı bilgisi
     */
    @Override
    public void createDoctor(User user) {
        logger.info("Creating doctor record for User ID: {}", user.getId());

        if (doctorRepository.findByUser(user).isPresent()) {
            logger.warn("Doctor record already exists for User ID: {}", user.getId());
            throw new BaseException(new ErrorMessage<>(MessageType.USERNAME_ALREADY_EXISTS, "Doctor record already exists"));
        }

        Doctor doctor = Doctor.builder()
                .user(user)
                .build();

        doctorRepository.save(doctor);
        logger.info("Doctor record created successfully for User ID: {}", user.getId());
    }

    /**
     * TC numarasına göre doktor detaylarını getirir.
     * @param tcNumber Doktorun TC numarası
     * @return DoctorResponse
     */
    @Override
    public DoctorResponse getDoctorByTcNumber(String tcNumber) {
        logger.info("Fetching doctor details for TC Number: {}", tcNumber);

        Doctor doctor = doctorRepository.findByUser_TcNumber(tcNumber)
                .orElseThrow(() -> new BaseException(new ErrorMessage<>(MessageType.DOCTOR_NOT_FOUND, "Doctor not found")));

        return DoctorResponse.builder()
                .firstName(doctor.getUser().getFirstName())
                .lastName(doctor.getUser().getLastName())
                .tcNumber(doctor.getUser().getTcNumber())
                .specialization(doctor.getSpecialization() != null ? doctor.getSpecialization() : "Not specified")
                .hospitalName(doctor.getHospital() != null ? doctor.getHospital().getName() : "Not specified")
                .build();
    }

    /**
     * Doktor detaylarını günceller.
     * @param tcNumber Doktorun TC numarası
     * @param specialization Yeni uzmanlık alanı
     * @param hospitalId Yeni hastane ID'si
     */
    @Override
    public void updateDoctorDetails(String tcNumber, String specialization, Long hospitalId) {
        logger.info("Updating doctor details for TC Number: {}", tcNumber);

        Doctor doctor = doctorRepository.findByUser_TcNumber(tcNumber)
                .orElseThrow(() -> new BaseException(new ErrorMessage<>(MessageType.DOCTOR_NOT_FOUND, "Doctor not found")));

        if (specialization != null && !specialization.isEmpty()) {
            doctor.setSpecialization(specialization);
        }

        if (hospitalId != null) {
            doctor.setHospital(Hospital.builder().id(hospitalId).build()); // Hospital ID ile ilişkilendir
        }

        doctorRepository.save(doctor);
        logger.info("Doctor details updated successfully for TC Number: {}", tcNumber);
    }
}