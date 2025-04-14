package com.xpronto.webgestao.domain.errors;

public class ExceptionResponse {
    private final int status;
    private final String reason;
    private final String message;
    private final String path;

    public ExceptionResponse(int status, String reason, String message, String path) {
        this.status = status;
        this.reason = reason;
        this.message = message;
        this.path = path;
    }

    public int getStatus() {
        return status;
    }

    public String getReason() {
        return reason;
    }

    public String getMessage() {
        return message;
    }

    public String getPath() {
        return path;
    }
}
