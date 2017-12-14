package com.henallux.smartcity.Model;

public class TokenReceived {
    private String token;
    private int code;

    public TokenReceived() {
    }

    public TokenReceived(String token, int code) {
        this.token = token;
        this.code = code;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
