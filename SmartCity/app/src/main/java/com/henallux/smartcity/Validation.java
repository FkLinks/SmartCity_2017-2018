package com.henallux.smartcity;

import android.widget.EditText;

import java.util.regex.Pattern;

public class Validation {

    public static boolean isValid(EditText editText, String regex, String errorMsg, String isEmptyMsg, Boolean required) {
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
