package com.henallux.smartcity.Model;

import java.io.Serializable;

public class User implements Serializable {
    private String UserName;
    private String Password;
    private String Email;
    private String sex;
    private String birthdate;
    private String geographicOrigins;

    public User() {
    }

    public User(String userName, String password, String email, String birthdate, String sex, String GeographicOrigins) {
        setUserName(userName);
        setPassword(password);
        setEmail(email);
        setBirthDate(birthdate);
        setSex(sex);
        setGeographicOrigins(GeographicOrigins);
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        this.UserName = userName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        this.Email = email;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        if(sex.equals("M") || sex.equals("F")) {
            this.sex = sex;
        }
    }

    public String getBirthDate() {
        return birthdate;
    }

    public void setBirthDate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getGeographicOrigins() {
        return geographicOrigins;
    }

    public void setGeographicOrigins(String geographicOrigins) {
        this.geographicOrigins = geographicOrigins;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        if(password.length() >= 6) {
            this.Password = password;
        }
    }
}
