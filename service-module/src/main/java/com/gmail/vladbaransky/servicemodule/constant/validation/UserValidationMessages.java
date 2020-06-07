package com.gmail.vladbaransky.servicemodule.constant.validation;

import static com.gmail.vladbaransky.servicemodule.constant.validation.UserValidationRules.*;

public interface UserValidationMessages {

    String NAME_SIZE_MESSAGE = "Name length should be from " + MIN_NAME_SIZE + " to " + MAX_NAME_SIZE + ".";
    String SURNAME_SIZE_MESSAGE = "Surname length should be from " + MIN_NAME_SIZE + " to " + MAX_NAME_SIZE + ".";
    String PATRONYMIC_SIZE_MESSAGE = "Patronymic length should be from " + MIN_NAME_SIZE + " to " + MAX_PATRONYMIC_SIZE + ".";
    String NAME_PATTERN_MESSAGE = "Name can contain only letters.";
    String SURNAME_PATTERN_MESSAGE = "Surname can contain only letters.";
    String PATRONYMIC_PATTERN_MESSAGE = "Patronymic can contain only letters.";
    String NOT_EMPTY_USERNAME_MESSAGE = "Username cannot be empty.";
    String USERNAME_PATTERN_MESSAGE = "Username does not match email pattern.";
    String ADDRESS_PATTERN_MESSAGE = "Address can contain letters, digits, spaces, commas and dots.";
    String TELEPHONE_PATTERN_MESSAGE = "Telephone can contain only digits, dashes and one plus symbol.";
}
