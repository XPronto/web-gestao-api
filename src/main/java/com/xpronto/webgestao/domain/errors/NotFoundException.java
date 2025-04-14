package com.xpronto.webgestao.domain.errors;

public class NotFoundException extends ApiException {
    public NotFoundException(String message) {
        super(message, 404, "Not Found");
    }
}
