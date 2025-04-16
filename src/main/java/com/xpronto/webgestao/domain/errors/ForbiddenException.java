package com.xpronto.webgestao.domain.errors;

public class ForbiddenException extends ApiException {
    public ForbiddenException(String message) {
        super(message, 403, "Forbidden");
    }
}
