package com.gmail.vladbaransky.servicemodule.util.password.impl;

import com.gmail.vladbaransky.servicemodule.config.PasswordEncoderConfig;
import com.gmail.vladbaransky.servicemodule.util.password.PasswordGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.passay.CharacterData;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;

@Component
public class PasswordGeneratorImpl implements PasswordGenerator {
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private static final String ERROR_PASSWORD = "error password";
    private static final String GET_CHARACTERS = "[email protected]#$%^&** ()__+";

    private final PasswordEncoderConfig passwordEncoder;

    public PasswordGeneratorImpl(PasswordEncoderConfig passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String generatePassword() {
        org.passay.PasswordGenerator gen = new org.passay.PasswordGenerator();
        CharacterData lowerCaseChars = EnglishCharacterData.LowerCase;
        CharacterRule lowerCaseRule = new CharacterRule(lowerCaseChars);
        lowerCaseRule.setNumberOfCharacters(2);

        CharacterData upperCaseChars = EnglishCharacterData.UpperCase;
        CharacterRule upperCaseRule = new CharacterRule(upperCaseChars);
        upperCaseRule.setNumberOfCharacters(2);

        CharacterData digitChars = EnglishCharacterData.Digit;
        CharacterRule digitRule = new CharacterRule(digitChars);
        digitRule.setNumberOfCharacters(2);

        CharacterData specialChars = new CharacterData() {
            public String getErrorCode() {
                return ERROR_PASSWORD;
            }

            public String getCharacters() {
                return GET_CHARACTERS;
            }
        };
        CharacterRule splCharRule = new CharacterRule(specialChars);
        splCharRule.setNumberOfCharacters(2);

        return gen.generatePassword(10, splCharRule, lowerCaseRule,
                upperCaseRule, digitRule);
    }

    @Override
    public String createBCryptPassword(String password) {
        logger.info("User password:" + password);
        return passwordEncoder.passwordEncoder().encode(password);
    }


}
