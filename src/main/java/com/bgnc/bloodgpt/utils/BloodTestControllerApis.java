package com.bgnc.bloodgpt.utils;

public class BloodTestControllerApis {

    public static final String API = "/api";
    public static final String BLOOD_TEST = "/blood-tests";
    public static final String ANALYZE = "/analyze";
    public static final String FILE = "file";
    public static final String UPLOAD = "/upload";
    public static final String USER_TESTS = "/{userId}";

    // ALL PATHS
    public static final String BLOOD_TEST_CONTROLLER = API + BLOOD_TEST;
    public static final String ANALYZE_BLOOD_TEST = BLOOD_TEST_CONTROLLER + ANALYZE;
    public static final String UPLOAD_BLOOD_TEST = BLOOD_TEST_CONTROLLER + UPLOAD;
    public static final String GET_USER_BLOOD_TESTS = BLOOD_TEST_CONTROLLER + USER_TESTS;
}