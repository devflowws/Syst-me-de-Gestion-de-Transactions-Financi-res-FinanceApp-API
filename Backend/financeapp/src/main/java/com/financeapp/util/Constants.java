package com.financeapp.util;

public class Constants {
    
    // API Endpoints
    public static final String API_VERSION = "/api/v1";
    public static final String AUTH_ENDPOINT = API_VERSION + "/auth";
    public static final String USER_ENDPOINT = API_VERSION + "/users";
    public static final String TRANSACTION_ENDPOINT = API_VERSION + "/transactions";
    public static final String ACCOUNT_ENDPOINT = API_VERSION + "/accounts";
    
    // Security
    public static final String[] PUBLIC_ENDPOINTS = {
        AUTH_ENDPOINT + "/login",
        AUTH_ENDPOINT + "/register",
        "/swagger-ui/**",
        "/api-docs/**"
    };
    
    public static final String[] ADMIN_ENDPOINTS = {
        USER_ENDPOINT + "/**"
    };
    
    // JWT
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    
    // Validation messages
    public static final String EMAIL_REQUIRED = "Email is required";
    public static final String EMAIL_INVALID = "Email is invalid";
    public static final String PASSWORD_REQUIRED = "Password is required";
    public static final String PASSWORD_WEAK = "Password must be at least 8 characters with uppercase, lowercase, number and special character";
    
    // Business rules
    public static final double MIN_TRANSACTION_AMOUNT = 0.01;
    public static final double MAX_TRANSACTION_AMOUNT = 1000000.00;
    
    // Date formats
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
}   