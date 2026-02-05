package com.web.pharma.utils;

import com.web.pharma.validations.ValidationRegex;

public interface CustomerValidation {

    static boolean isValidPhone(String phone) {
        return phone != null && phone.matches(ValidationRegex.PHONE_REGEX);
    }

    static boolean isValidEmail(String email) {
        return email != null &&
                email.matches(ValidationRegex.EMAIL_REGEX);
    }
}

