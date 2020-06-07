package com.gmail.vladbaransky.servicemodule.util.password;

public interface PasswordGenerator {
    String generatePassword();

    String createBCryptPassword(String password);
}
