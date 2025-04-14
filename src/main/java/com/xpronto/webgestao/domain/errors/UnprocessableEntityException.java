package com.xpronto.webgestao.domain.errors;

public class UnprocessableEntityException extends ApiException {
    public UnprocessableEntityException(String message) {
        super(message, 422, "Unprocessable Entity");
    }
}
