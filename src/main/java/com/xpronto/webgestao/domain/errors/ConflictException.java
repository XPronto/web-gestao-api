package com.xpronto.webgestao.domain.errors;

public class ConflictException extends ApiException {
    public ConflictException(String message) {
        super(message, 409, "Conflict");
    }
}
