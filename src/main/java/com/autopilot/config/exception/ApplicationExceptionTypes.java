package com.autopilot.config.exception;

import org.springframework.http.HttpStatus;

public class ApplicationExceptionTypes {

    public static final Triple INVALID_PARAMS = new Triple(1001, HttpStatus.BAD_REQUEST, "Invalid parameter: %s. Required type: %s.");
    public static final Triple MISSING_HEADER = new Triple(1002, HttpStatus.BAD_REQUEST, "Missing header: %s");
    public static final Triple REQUEST_ERROR = new Triple(1003, HttpStatus.BAD_REQUEST, "Bad request.");
    public static final Triple GENERIC_EXCEPTION = new Triple(1004, HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong.");
    public static final Triple INVALID_HTTP_METHOD_REQUEST = new Triple(1005, HttpStatus.METHOD_NOT_ALLOWED, "Unsupported method. Supported: %s");

    public static final Triple METHOD_NOT_SUPPORTED = new Triple(1006, HttpStatus.METHOD_NOT_ALLOWED, "Unsupported method. Supported: %s");


    public record Triple(int code, HttpStatus status, String message) {}
}
