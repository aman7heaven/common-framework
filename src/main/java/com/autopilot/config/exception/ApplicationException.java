package com.autopilot.config.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

/**
 * Model required to handle the exception
 */

@EqualsAndHashCode(callSuper = true)
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"cause", "stackTrace", "status", "suppressed", "localizedMessage", "headers"})
public class ApplicationException extends RuntimeException {
    private Integer code;
    private HttpStatus status;
    private String message;
    private Object details;
    private HttpHeaders headers;

    public ApplicationException(
            @NotNull ApplicationExceptionTypes applicationExceptionType,
            HttpHeaders headers,
            Object details
    ) {
        this.code = applicationExceptionType.code();
        this.status = applicationExceptionType.status();
        this.message = applicationExceptionType.message();
        this.details = details;
        this.headers = headers;
    }

    public ApplicationException(
            @NotNull ApplicationExceptionTypes applicationExceptionType,
            Object details
    ) {
        this.code = applicationExceptionType.code();
        this.status = applicationExceptionType.status();
        this.message = applicationExceptionType.message();
        this.details = details;
    }

    public ApplicationException(
            @NotNull ApplicationExceptionTypes applicationExceptionType,
            HttpHeaders headers
    ) {
        this.code = applicationExceptionType.code();
        this.status = applicationExceptionType.status();
        this.message = applicationExceptionType.message();
        this.headers = headers;
    }

    public ApplicationException(
            @NotNull ApplicationExceptionTypes applicationExceptionType
    ) {
        this.code = applicationExceptionType.code();
        this.status = applicationExceptionType.status();
        this.message = applicationExceptionType.message();
    }

    public ApplicationException(
            Integer code,
            HttpStatus status,
            String message)
    {
        this.code = code;
        this.status = status;
        this.message = message;
    }
}
