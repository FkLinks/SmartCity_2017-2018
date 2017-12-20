package com.henallux.smartcity;

import android.widget.EditText;

import java.util.regex.Pattern;

public class Validation {
    //regex
    private static final String EMAIL_REGEX = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
    private static final String BIRTHDATE_REGEX = "[1-2][0-9]{3}-[0-1][0-9]-[0-3][0-9]";
    private static final String PASSWORD_REGEX = "[A-Za-z\\d$@!%*#?&]{6,}$";

    //error messages
    private static final String REQUIRED_MSG = "Required";

    private static final String EMAIL_NOT_VALID = "Respectez le format example@ex.com";
    private static final String BIRTHDATE_NOT_VALID = "Respectez le format yyyy-mm-dd";
    private static final String PASSWORD_NOT_VALID = "Doit contenir au moins 6 caracteres";
    
    public static boolean isEmailAdressValid(EditText editText, String errorMsg, String isEmptyMsg, Boolean required){
        return isValid(editText, EMAIL_REGEX, errorMsg, isEmptyMsg, required);
    }

    public static boolean isBirthdateValid(EditText editText, String errorMsg, String isEmptyMsg, Boolean required){
        return isValid(editText, BIRTHDATE_REGEX, errorMsg, isEmptyMsg, required);
    }

    public static boolean isPasswordValid(EditText editText, String errorMsg, String isEmptyMsg, Boolean required){
        return isValid(editText, PASSWORD_REGEX, errorMsg, isEmptyMsg, required);
    }

    private static boolean isValid(EditText editText, String regex, String errorMsg, String isEmptyMsg, Boolean required) {
        String text = editText.getText().toString().trim();

        editText.setError(null);

        if(required && !hasText(editText, isEmptyMsg)) return false;

        if(!Pattern.matches(regex, text)){
            editText.setError(errorMsg);
            return false;
        }

        return true;
    }

    public static boolean hasText(EditText editText, String isEmptyMsg) {
        String text = editText.getText().toString().trim();

        editText.setError(null);

        if(text.length() == 0){
            editText.setError(isEmptyMsg);
            return false;
        }
        return true;
    }


}
