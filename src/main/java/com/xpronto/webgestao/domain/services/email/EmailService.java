package com.xpronto.webgestao.domain.services.email;

public interface EmailService {
    public void sendInviteEmail(String userEmail, String firstName, String token);
}
