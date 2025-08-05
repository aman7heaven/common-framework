package com.autopilot.config.exception;

import com.autopilot.config.logging.AppLogger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.http.converter.HttpMessageNotReadableException;

/**
 * Global Exception Handler
 * This handles all common exceptions and formats a proper API response.
 */
@ControllerAdvice
public class ErrorController {

    private final AppLogger log = new AppLogger(LoggerFactory.getLogger(ErrorController.class));

    /**
     * Handle custom application exceptions (your own thrown errors).
     */
    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ApplicationException> handleAppException(ApplicationException ex) {
        log.error("ApplicationException occurred: " + ex.getMessage(), ex);
        return new ResponseEntity<>(ex, ex.getHeaders(), ex.getStatus());
    }

    /**
     * Handle incorrect path or query param data types (e.g., passing "abc" when expecting Long).
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApplicationException> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        log.error("MethodArgumentTypeMismatchException: " + ex.getMessage(), ex);

        String formattedMsg = ApplicationExceptionTypes.INVALID_PARAMS.message()
                .formatted(ex.getName(), ex.getRequiredType().getSimpleName());

        return ResponseEntity.status(ApplicationExceptionTypes.INVALID_PARAMS.status())
                .body(new ApplicationException(
                        ApplicationExceptionTypes.INVALID_PARAMS.code(),
                        ApplicationExceptionTypes.INVALID_PARAMS.status(),
                        formattedMsg));
    }

    /**
     * Triggered when a required header is missing from the request.
     */
    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<ApplicationException> handleMissingHeader(MissingRequestHeaderException ex) {
        log.warn("MissingRequestHeaderException: " + ex.getMessage());

        String msg = ApplicationExceptionTypes.MISSING_HEADER.message().formatted(ex.getHeaderName());

        return ResponseEntity.status(ApplicationExceptionTypes.MISSING_HEADER.status())
                .body(new ApplicationException(
                        ApplicationExceptionTypes.MISSING_HEADER.code(),
                        ApplicationExceptionTypes.MISSING_HEADER.status(),
                        msg));
    }

    /**
     * Triggered when a request param (e.g., ?page=) is missing.
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApplicationException> handleMissingParam(MissingServletRequestParameterException ex) {
        log.warn("MissingServletRequestParameterException: " + ex.getMessage());

        return ResponseEntity.status(ApplicationExceptionTypes.REQUEST_ERROR.status())
                .body(new ApplicationException(
                        ApplicationExceptionTypes.REQUEST_ERROR.code(),
                        ApplicationExceptionTypes.REQUEST_ERROR.status(),
                        ApplicationExceptionTypes.REQUEST_ERROR.message()));
    }

    /**
     * Triggered when the JSON in the request body is malformed or unreadable.
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApplicationException> handleUnreadableJson(HttpMessageNotReadableException ex) {
        log.warn("HttpMessageNotReadableException: " + ex.getMessage());

        return ResponseEntity.status(ApplicationExceptionTypes.GENERIC_EXCEPTION.status())
                .body(new ApplicationException(
                        ApplicationExceptionTypes.GENERIC_EXCEPTION.code(),
                        ApplicationExceptionTypes.GENERIC_EXCEPTION.status(),
                        ApplicationExceptionTypes.GENERIC_EXCEPTION.message()));
    }

    /**
     * Triggered when HTTP method used is invalid (e.g., POST instead of GET).
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApplicationException> handleMethodNotAllowed(HttpRequestMethodNotSupportedException ex) {
        log.warn("HttpRequestMethodNotSupportedException: " + ex.getMessage());

        String supported = String.join(", ", ex.getSupportedMethods());
        String msg = ApplicationExceptionTypes.INVALID_HTTP_METHOD_REQUEST.message().formatted(supported);

        return ResponseEntity.status(ApplicationExceptionTypes.INVALID_HTTP_METHOD_REQUEST.status())
                .body(new ApplicationException(
                        ApplicationExceptionTypes.INVALID_HTTP_METHOD_REQUEST.code(),
                        ApplicationExceptionTypes.INVALID_HTTP_METHOD_REQUEST.status(),
                        msg));
    }

    /**
     * Catch-all for any uncaught except
     * ions to prevent app crash and give meaningful response.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApplicationException> handleUnexpectedException(Exception ex) {
        log.error("Unexpected Exception: " + ex.getMessage(), ex);

        return ResponseEntity.status(ApplicationExceptionTypes.GENERIC_EXCEPTION.status())
                .body(new ApplicationException(
                        ApplicationExceptionTypes.GENERIC_EXCEPTION.code(),
                        ApplicationExceptionTypes.GENERIC_EXCEPTION.status(),
                        ApplicationExceptionTypes.GENERIC_EXCEPTION.message(),
                        ex.getMessage()));
    }
}
