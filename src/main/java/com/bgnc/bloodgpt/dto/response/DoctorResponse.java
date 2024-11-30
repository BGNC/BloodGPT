package com.bgnc.bloodgpt.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class DoctorResponse {
    private String firstName;
    private String lastName;
    private String tcNumber;
    private String specialization;
    private String hospitalName;
    private String districtName;
    private String cityName;
}
