package com.xpronto.webgestao.infrastructure.config.integrations;

import com.xpronto.webgestao.domain.errors.InternalException;

import feign.Response;
import feign.codec.ErrorDecoder;

public class IntegrationsErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        return new InternalException("Tenant address geocoding error.");
    }

}
