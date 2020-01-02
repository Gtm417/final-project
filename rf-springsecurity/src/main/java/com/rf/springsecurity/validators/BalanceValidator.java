package com.rf.springsecurity.validators;

import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Service
public class BalanceValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Long.class.equals(clazz);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "balance", "balance.empty");
        Long val = (Long) obj;
        if (val < 0) {
            errors.rejectValue("balance", "value.negative");
        }
    }
}
