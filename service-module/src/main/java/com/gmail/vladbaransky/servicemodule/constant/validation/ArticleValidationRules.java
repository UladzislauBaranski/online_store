package com.gmail.vladbaransky.servicemodule.constant.validation;

public interface ArticleValidationRules {

    int MAX_TITLE_SIZE = 50;
    int MAX_SUMMARY_SIZE = 50;
    int MAX_COMMENT_SIZE = 1000;
    int MAX_CONTENT_SIZE = 1000;
    String TITLE_PATTERN = "^[a-zA-Z0-9 ,-]*$";
    String SUMMARY_PATTERN = "^[a-zA-Z0-9 ,-]*$";
    String DATE_PATTERN = "yyyy-MM-dd";
}