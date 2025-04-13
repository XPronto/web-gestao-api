package com.xpronto.webgestao.domain.services;

public interface EncoderService {
    String encode(String rawText);
    boolean matches(String rawText, String encodedText);
}
