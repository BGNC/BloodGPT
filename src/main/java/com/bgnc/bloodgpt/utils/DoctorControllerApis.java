package com.bgnc.bloodgpt.utils;

public class DoctorControllerApis {

    public static final String API = "/api";
    public static final String DOCTOR = "/doctor";
    public static final String DETAILS = "/{tcNumber}";
    public static final String UPDATE_DETAILS = "/{tcNumber}/update-details";

    // Tam Yollar
    public static final String DOCTOR_CONTROLLER = API + DOCTOR;
    public static final String GET_DOCTOR_DETAILS = DOCTOR_CONTROLLER + DETAILS;
    public static final String UPDATE_DOCTOR_DETAILS = DOCTOR_CONTROLLER + UPDATE_DETAILS;
}
