package com.autopilot.config.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

@EqualsAndHashCode(callSuper = true)
@Data
public class ApplicationException extends Exception {

    private final int code;
    private final HttpStatus status;
    private final String message;
    private final Object details;
    private final HttpHeaders headers;

    public ApplicationException(int code, HttpStatus status, String message) {
        this.code = code;
        this.status = status;
        this.message = message;
        this.details = null;
        this.headers = null;
    }

    public ApplicationException(int code, HttpStatus status, String message, Object details) {
        this.code = code;
        this.status = status;
        this.message = message;
        this.details = details;
        this.headers = null;
    }

}
