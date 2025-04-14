package com.xpronto.webgestao.domain.errors;

public class InternalException extends ApiException {

    public InternalException(String message) {
        super(message, 500, "Internal Error");
    }

}
