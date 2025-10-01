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
            @NotNull ApplicationExceptionTypes applicationExceptionTypes,
            HttpHeaders headers,
            Object details
    ) {
        this.code = applicationExceptionTypes.code();
        this.status = applicationExceptionTypes.status();
        this.message = applicationExceptionTypes.message();
        this.details = details;
        this.headers = headers;
    }

    public ApplicationException(
            @NotNull ApplicationExceptionTypes applicationExceptionTypes,
            Object details
    ) {
        this.code = applicationExceptionTypes.code();
        this.status = applicationExceptionTypes.status();
        this.message = applicationExceptionTypes.message();
        this.details = details;
    }

    public ApplicationException(
            @NotNull ApplicationExceptionTypes applicationExceptionTypes,
            HttpHeaders headers
    ) {
        this.code = applicationExceptionTypes.code();
        this.status = applicationExceptionTypes.status();
        this.message = applicationExceptionTypes.message();
        this.headers = headers;
    }

    public ApplicationException(
            @NotNull ApplicationExceptionTypes applicationExceptionTypes
    ) {
        this.code = applicationExceptionTypes.code();
        this.status = applicationExceptionTypes.status();
        this.message = applicationExceptionTypes.message();
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
