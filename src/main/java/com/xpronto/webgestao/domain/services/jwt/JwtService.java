package com.xpronto.webgestao.domain.services.jwt;

import com.xpronto.webgestao.domain.model.User;

public interface JwtService {
    public Tokens signAuthTokens(User user);
    public UserPayload verify(String token, TokenType type) throws Exception;
}
