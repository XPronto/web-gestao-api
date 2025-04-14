package com.xpronto.webgestao.domain.errors;

public class BadRequestException extends ApiException {
    public BadRequestException(String message) {
        super(message, 400, "Bad Request");
    }
}
