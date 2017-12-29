package com.henallux.smartcity.Model;

import java.util.Date;

public class TokenReceived {
    private String token;
    private int code;
    private Date expirationDate;

    public TokenReceived() {
    }

    public TokenReceived(String token, int code, Date expirationDate) {
        this.token = token;
        this.code = code;
        this.expirationDate = expirationDate;
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

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }
}
