package com.bgnc.bloodgpt.dto.response;



import lombok.Data;

@Data
public class ProfileResponse {
    private String firstName; // user name
    private String lastName; // user lastname
    private Integer age; // age
    private Double height; // height
    private Double weight; // wieght
    private String email; // email
    private String phoneNumber; // phone
    private Double bmi;
}