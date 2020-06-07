package com.gmail.vladbaransky.servicemodule.constant.validation;

public interface ItemValidationMessages {

    String NOT_EMPTY_ITEM_TITLE_MESSAGE = "Item title cannot be empty.";
    String ITEM_TITLE_PATTERN_MESSAGE = "Item can contain letters, digits and spaces";
    String ITEM_SUMMARY_SIZE_MESSAGE = "Summary length should be less than " + ItemValidationRules.MAX_ITEM_SUMMARY_SIZE;
    String PRICE_FORMAT_MESSAGE = "Price can contain 4 digits before the decimal point and 3 after it";
    String NOT_NULL_PRICE_MESSAGE = "Price cannot be empty";
    String MAX_PRICE_MESSAGE = "Price should be less than " + ItemValidationRules.MAX_PRICE;
    String MIN_PRICE_MESSAGE = "Price should be more than " + ItemValidationRules.MIN_PRICE;

}
