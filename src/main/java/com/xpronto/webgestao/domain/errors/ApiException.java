package com.xpronto.webgestao.domain.errors;

public class ApiException extends RuntimeException {
    private int status;
    private String reason;

    public ApiException(String message, int status, String reason) {
        super(message);
        this.status = status;
        this.reason = reason;
    }

    public int getStatus() {
        return status;
    }

    public String getReason() {
        return reason;
    }

    public ExceptionResponse mapToResponse(String path) {
        return new ExceptionResponse(status, reason, this.getMessage(), path);
    }
}
