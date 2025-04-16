package com.xpronto.webgestao.api.exceptionhandler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.xpronto.webgestao.domain.errors.ApiException;
import com.xpronto.webgestao.domain.errors.ExceptionResponse;
import com.xpronto.webgestao.domain.errors.InternalException;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ApiException.class)
    private ResponseEntity<ExceptionResponse> apiExceptionHandler(ApiException exception, HttpServletRequest request) {
        return ResponseEntity.status(exception.getStatus()).body(exception.mapToResponse(request.getRequestURI()));
    }

    @ExceptionHandler(Exception.class)
    private ResponseEntity<ExceptionResponse> globalExceptionHandler(Exception exception, HttpServletRequest request) {
        exception.printStackTrace();
        var internal = new InternalException("An unexpected error occurred.");

        return ResponseEntity.status(500).body(internal.mapToResponse(request.getRequestURI()));
    }
}
