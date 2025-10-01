package com.autopilot.config.exception;

import org.springframework.http.HttpStatus;

/**
 * Which contains Application Exception Types
 */

public record ApplicationExceptionTypes(Integer code, HttpStatus status, String message) {
    public static final ApplicationExceptionTypes INVALID_PARAMS = new ApplicationExceptionTypes(1, HttpStatus.BAD_REQUEST, "Invalid value for %s, expected type %s");
    public static final ApplicationExceptionTypes MISSING_HEADER = new ApplicationExceptionTypes(2, HttpStatus.BAD_REQUEST, "Required header %s is missing in your request.");
    public static final ApplicationExceptionTypes REQUEST_ERROR = new ApplicationExceptionTypes(3, HttpStatus.BAD_REQUEST, "Please check your request.");
    public static final ApplicationExceptionTypes REQUIRED_REQUEST_PARAM_KEY_MISSING =
            new ApplicationExceptionTypes(4,  HttpStatus.UNPROCESSABLE_ENTITY, "Please check as required key %s missing from your request.");
    public static final ApplicationExceptionTypes GENERIC_BAD_REQUEST = new ApplicationExceptionTypes(5, HttpStatus.BAD_REQUEST, "Something is wrong in the request. Please try again");
    public static final ApplicationExceptionTypes RESOURCE_NOT_FOUND = new ApplicationExceptionTypes(6, HttpStatus.NOT_FOUND, "Resource not found");
    public static final ApplicationExceptionTypes INVALID_HTTP_METHOD = new ApplicationExceptionTypes(
            7,
            HttpStatus.METHOD_NOT_ALLOWED,
            "Please make request with correct HTTP method. Supported Methods are %s"
    );
    public static final ApplicationExceptionTypes GENERIC_EXCEPTION = new ApplicationExceptionTypes(8, HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong. Please try again later.");

    public static final ApplicationExceptionTypes REQUEST_BODY_VALIDATION_ERROR = new ApplicationExceptionTypes(9, HttpStatus.BAD_REQUEST, "Something is wrong with the request body");
    public static final ApplicationExceptionTypes REQUEST_BODY_CONTAINS_XSS = new ApplicationExceptionTypes(10, HttpStatus.BAD_REQUEST, "Please check your request.");

    public static final ApplicationExceptionTypes TO_MANY_LOGIN_ATTEMPTS = new ApplicationExceptionTypes(11, HttpStatus.TOO_MANY_REQUESTS, "Too many failed Attempts. Please try again after 10 minutes.");
    public static final ApplicationExceptionTypes INVALID_AUTH_DETAIL = new ApplicationExceptionTypes(12, HttpStatus.BAD_REQUEST, "Invalid Credentials, Please login with valid credentials details");

    public static final ApplicationExceptionTypes UNAUTHORIZED = new ApplicationExceptionTypes(13, HttpStatus.UNAUTHORIZED, "Unauthorized");
    public static final ApplicationExceptionTypes METHOD_NOT_SUPPORTED = new ApplicationExceptionTypes(32, HttpStatus.METHOD_NOT_ALLOWED, "Method not supported: %s");
}
