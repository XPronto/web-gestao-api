package com.xpronto.webgestao.domain.usecases;

public interface UseCase<C, R> {
    public R execute(C command) throws Exception;
}
