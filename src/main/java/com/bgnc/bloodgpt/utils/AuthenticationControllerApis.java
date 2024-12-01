package com.bgnc.bloodgpt.utils;

public class AuthenticationControllerApis {

    public static final String API = "/api";
    public static final String AUTH = "/auth";
    public static final String REGISTER = "/register";
    public static final String LOGIN = "/login";

    // Tam Yollar
    public static final String AUTH_CONTROLLER = API + AUTH;
    public static final String REGISTER_USER = AUTH_CONTROLLER + REGISTER;
    public static final String LOGIN_USER = AUTH_CONTROLLER + LOGIN;
    public static final String LOGOUT_USER = AUTH_CONTROLLER + LOGIN;
    public static final String SECURE_CONFIG_PERMIT_ALL = AUTH_CONTROLLER+"/**";
}