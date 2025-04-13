package com.xpronto.webgestao.infrastructure.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.xpronto.webgestao.domain.services.EncoderService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EncoderServiceImpl implements EncoderService {
    private final PasswordEncoder passwordEncoder;

    @Override
    public String encode(String rawText) {
        return passwordEncoder.encode(rawText);
    }

    @Override
    public boolean matches(String rawText, String encodedText) {
        return passwordEncoder.matches(rawText, encodedText);
    }

}
