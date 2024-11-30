package com.bgnc.bloodgpt.service;



import com.bgnc.bloodgpt.dto.response.DoctorResponse;
import com.bgnc.bloodgpt.model.User;

public interface DoctorService {


    void createDoctor(User user);

    /**
     * Get details of a doctor by their TC number.
     *
     * @param tcNumber The TC number of the doctor.
     * @return DoctorResponse containing doctor details.
     */
    DoctorResponse getDoctorByTcNumber(String tcNumber);

    /**
     * Update specialization and hospital details of a doctor.
     *
     * @param tcNumber       The TC number of the doctor.
     * @param specialization The new specialization of the doctor.
     * @param hospitalId     The ID of the hospital where the doctor works.
     */
    void updateDoctorDetails(String tcNumber, String specialization, Long hospitalId);
}
