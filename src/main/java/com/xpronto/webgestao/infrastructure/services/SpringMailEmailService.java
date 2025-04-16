package com.xpronto.webgestao.infrastructure.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.xpronto.webgestao.domain.services.email.EmailService;

import jakarta.mail.Message;
import jakarta.mail.internet.InternetAddress;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SpringMailEmailService implements EmailService {
    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    @Value("${api.client-url}")
    private String clientUrl;

    @Override
    @Async
    public void sendInviteEmail(String userEmail, String firstName, String token) {
        String subject = "Convite para criar sua conta";
        String url = clientUrl + "/confirmar-conta?token=" + token;

        Context context = new Context();
        context.setVariable("name", firstName);
        context.setVariable("activationLink", url);

        String content = templateEngine.process("emails/invite", context);

        MimeMessagePreparator message = preparator -> {
            preparator.setRecipient(Message.RecipientType.TO, new InternetAddress(userEmail));
            preparator.setFrom("nao-responder@xpronto.com");
            preparator.setSubject(subject);
            preparator.setContent(content, "text/html");
        };

        mailSender.send(message);
    }

}
