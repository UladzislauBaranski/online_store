package com.gmail.vladbaransky.servicemodule.constant.validation;

public interface ItemValidationRules {

    String ITEM_TITLE_PATTERN = "^[a-zA-Z0-9 ]*$";
    int MAX_ITEM_SUMMARY_SIZE = 200;
    String MIN_PRICE = "0.0";
    String MAX_PRICE = "99999";
    int PRICE_INTEGER_PART_SIZE = 5;
    int PRICE_FRACTION_PART_SIZE = 3;

}