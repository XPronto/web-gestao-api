package com.xpronto.webgestao.domain.errors;

public class UnauthorizedException extends ApiException {
    public UnauthorizedException(String message) {
        super(message, 401, "Unauthorized");
    }
}
