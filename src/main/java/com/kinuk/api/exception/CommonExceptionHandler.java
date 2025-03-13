package com.kinuk.api.exception;

import com.kinuk.api.util.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class CommonExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Type Not Accept Data
     */
    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return new ResponseEntity<>(ApiResponse.fail(ex.getMessage()), status);
    }

    /**
     * Not Json Data
     */
    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return new ResponseEntity<>(ApiResponse.fail(ex.getMessage()), status);
    }

    /**
     * 400 Bad Request 처리
     */
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return new ResponseEntity<>(ApiResponse.fail(ex.getMessage()), status);
    }

    /**
     * ApiException 처리
     */
    @ExceptionHandler(ApiException.class)
    protected ResponseEntity<Object> handleApiException(ApiException ex) {
        logger.info("Api Exception", ex);
        return new ResponseEntity<>(ApiResponse.fail(ex.getApiResponseCode().getMessage()), ex.getApiResponseCode().getHttpStatus());
    }

    /**
     * 유효성 검증 실패한 경우 handler
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }

        return new ResponseEntity<>(ApiResponse.fail(errors, ApiResponseCode.INVALID_REQUEST.getMessage()), HttpStatus.OK);
    }

    /**
     * RestController Global exception handler
     */
    @ExceptionHandler
    protected ApiResponse<?> handleApiException(Exception ex) {
        logger.error("Global Exception", ex);
        return ApiResponse.error(ex.getMessage());
    }
}
