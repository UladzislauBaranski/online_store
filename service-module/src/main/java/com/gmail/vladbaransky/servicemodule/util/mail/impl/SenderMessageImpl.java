package com.gmail.vladbaransky.servicemodule.util.mail.impl;

import com.gmail.vladbaransky.servicemodule.util.mail.SenderMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class SenderMessageImpl implements SenderMessage {

    private final JavaMailSender emailSender;

    public SenderMessageImpl(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    @Override
    public void sendMessage(
            String to, String subject, String text) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }
}