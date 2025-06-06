package com.xpronto.webgestao.api.exceptionhandler;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.xpronto.webgestao.domain.errors.ApiException;
import com.xpronto.webgestao.domain.errors.ExceptionResponse;
import com.xpronto.webgestao.domain.errors.ForbiddenException;
import com.xpronto.webgestao.domain.errors.InternalException;
import com.xpronto.webgestao.domain.errors.UnprocessableEntityException;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ApiException.class)
    private ResponseEntity<ExceptionResponse> apiExceptionHandler(ApiException exception, HttpServletRequest request) {
        return ResponseEntity.status(exception.getStatus()).body(exception.mapToResponse(request.getRequestURI()));
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    private ResponseEntity<ExceptionResponse> accessDeniedHandler(AuthorizationDeniedException exception, HttpServletRequest request){
        var apiException = new ForbiddenException(exception.getMessage());

        return ResponseEntity.status(apiException.getStatus()).body(apiException.mapToResponse(request.getServletPath()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    private ResponseEntity<ExceptionResponse> argumentNotValidHandler(MethodArgumentNotValidException exception, HttpServletRequest request) {
        FieldError fieldError = exception.getFieldError();

        var exceptionApi = new UnprocessableEntityException(fieldError != null ? fieldError.getDefaultMessage() : "Error on validating field.");

        return ResponseEntity.status(exceptionApi.getStatus()).body(exceptionApi.mapToResponse(request.getServletPath()));
    }

    @ExceptionHandler(Exception.class)
    private ResponseEntity<ExceptionResponse> globalExceptionHandler(Exception exception, HttpServletRequest request) {
        exception.printStackTrace();
        var internal = new InternalException("An unexpected error occurred.");

        return ResponseEntity.status(internal.getStatus()).body(internal.mapToResponse(request.getRequestURI()));
    }
}
