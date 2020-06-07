package com.gmail.vladbaransky.servicemodule.util.mail;

public interface SenderMessage {
    void sendMessage(String to, String subject, String text);
}
