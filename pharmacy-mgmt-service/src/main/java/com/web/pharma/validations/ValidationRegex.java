package com.web.pharma.validations;

public final class ValidationRegex {

    private ValidationRegex() {
        // prevent instantiation
    }

    public static final String PHONE_REGEX = "^\\+?[0-9]{10,15}$";
    public static final String EMAIL_REGEX =
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
}

