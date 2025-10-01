package com.autopilot.config.exception;

import com.autopilot.config.logging.AppLogger;
import com.autopilot.utils.JsonUtils;
import com.fasterxml.jackson.core.JsonParseException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

/**
 * Global level error handler
 */
@ControllerAdvice
public class ErrorController {

    private final AppLogger log = new AppLogger(LoggerFactory.getLogger(ErrorController.class));

    @Autowired
    private HttpServletRequest httpServletRequest;

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ApplicationException> handleCustomErrorExceptions(@NotNull Exception e) {
        log.debug("handleCustomErrorExceptions - Exception thrown - {}", e.getMessage());
        ApplicationException applicationException = (ApplicationException) e;
        return ResponseEntity
                .status(applicationException.getStatus())
                .headers(applicationException.getHeaders())
                .body(applicationException);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApplicationException> handleTypeMismatchException(
            @NotNull MethodArgumentTypeMismatchException e
    ) {
        String fieldName = e.getName();
        String requiredType = e.getRequiredType() != null ? e.getRequiredType().getSimpleName() : "Unknown";
        log.info("handleTypeMismatchException - Exception thrown - {}, stackTrace - {}", e.getMessage(), ExceptionUtils.getStackTrace(e));

        return ResponseEntity
                .status(ApplicationExceptionTypes.INVALID_PARAMS.status())
                .body(new ApplicationException(
                        ApplicationExceptionTypes.INVALID_PARAMS.code(),
                        ApplicationExceptionTypes.INVALID_PARAMS.status(),
                        String.format(ApplicationExceptionTypes.INVALID_PARAMS.message(), fieldName, requiredType)
                ));
    }

    @ExceptionHandler(MissingServletRequestPartException.class)
    public ResponseEntity<ApplicationException> handleRequestParamKeyMissingException(MissingServletRequestPartException e) {
        String key = e.getRequestPartName();
        log.info("handleRequestParamKeyMissingException - Exception thrown - {}, stackTrace - {}", e.getMessage(), ExceptionUtils.getStackTrace(e));

        return ResponseEntity
                .status(ApplicationExceptionTypes.REQUIRED_REQUEST_PARAM_KEY_MISSING.status())
                .body(new ApplicationException(
                        ApplicationExceptionTypes.REQUIRED_REQUEST_PARAM_KEY_MISSING.code(),
                        ApplicationExceptionTypes.REQUIRED_REQUEST_PARAM_KEY_MISSING.status(),
                        String.format(ApplicationExceptionTypes.REQUIRED_REQUEST_PARAM_KEY_MISSING.message(), key)
                ));
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<ApplicationException> handleRequestHeaderException(MissingRequestHeaderException e) {
        log.info("handleRequestHeaderException - Exception thrown - {}, stackTrace - {}", e.getMessage(), ExceptionUtils.getStackTrace(e));

        return ResponseEntity
                .status(ApplicationExceptionTypes.MISSING_HEADER.status())
                .body(new ApplicationException(
                        ApplicationExceptionTypes.MISSING_HEADER.code(),
                        ApplicationExceptionTypes.MISSING_HEADER.status(),
                        String.format(ApplicationExceptionTypes.MISSING_HEADER.message(), e.getHeaderName())
                ));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApplicationException> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.info("handleHttpMessageNotReadableException - Exception thrown - {}, stackTrace - {}", e.getMessage(), ExceptionUtils.getStackTrace(e));

        return ResponseEntity
                .status(ApplicationExceptionTypes.GENERIC_BAD_REQUEST.status())
                .body(new ApplicationException(ApplicationExceptionTypes.GENERIC_BAD_REQUEST));
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ApplicationException> handleBindException(BindException e) {
        log.info("handleBindException - Exception thrown - {}, stackTrace - {}", e.getMessage(), ExceptionUtils.getStackTrace(e));

        return ResponseEntity
                .status(ApplicationExceptionTypes.REQUEST_ERROR.status())
                .body(new ApplicationException(ApplicationExceptionTypes.REQUEST_ERROR));
    }

    @ExceptionHandler(JsonParseException.class)
    public ResponseEntity<ApplicationException> handleJsonParseException(JsonParseException e) {
        log.info("handleJsonParseException - Exception thrown - {}, stackTrace - {}", e.getMessage(), ExceptionUtils.getStackTrace(e));

        return ResponseEntity
                .status(ApplicationExceptionTypes.REQUEST_ERROR.status())
                .body(new ApplicationException(ApplicationExceptionTypes.REQUEST_ERROR));
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ApplicationException> handleNoResourceFoundException(NoResourceFoundException e) {
        log.info("handleNoResourceFoundException - Exception thrown - {}, for path: {}", e.getMessage(), e.getResourcePath());

        return ResponseEntity
                .status(ApplicationExceptionTypes.RESOURCE_NOT_FOUND.status())
                .body(new ApplicationException(ApplicationExceptionTypes.RESOURCE_NOT_FOUND));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApplicationException> handleMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error("handleMethodNotSupportedException - Exception thrown - {}, stackTrace - {}", e.getMessage(), ExceptionUtils.getStackTrace(e));

        return ResponseEntity
                .status(ApplicationExceptionTypes.INVALID_HTTP_METHOD.status())
                .body(new ApplicationException(
                        ApplicationExceptionTypes.INVALID_HTTP_METHOD.code(),
                        ApplicationExceptionTypes.INVALID_HTTP_METHOD.status(),
                        String.format(ApplicationExceptionTypes.INVALID_HTTP_METHOD.message(), JsonUtils.toJson(e.getSupportedMethods()))
                ));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApplicationException> handleRequestParameterException(MissingServletRequestParameterException e) {
        log.error("handleRequestParameterException - Exception thrown - {}, stackTrace - {}", e.getMessage(), ExceptionUtils.getStackTrace(e));

        return ResponseEntity
                .status(ApplicationExceptionTypes.REQUEST_ERROR.status())
                .body(new ApplicationException(ApplicationExceptionTypes.REQUEST_ERROR));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApplicationException> handleExceptions(Exception e) {
        log.error("handleExceptions - Exception thrown - {}, stackTrace - {}", e.getMessage(), ExceptionUtils.getStackTrace(e));

        return ResponseEntity
                .status(ApplicationExceptionTypes.GENERIC_EXCEPTION.status())
                .body(new ApplicationException(
                        ApplicationExceptionTypes.GENERIC_EXCEPTION,
                        ExceptionUtils.getStackTrace(e)
                ));
    }
}
