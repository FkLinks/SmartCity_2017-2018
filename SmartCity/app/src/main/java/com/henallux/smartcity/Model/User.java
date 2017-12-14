package com.henallux.smartcity.Model;

import java.util.Date;
import java.util.StringTokenizer;

public class User {
    private String userName;
    private String email;
    private char sex;
    private Date birthdate;
    private String geoOrigin;
    private String phoneNumber;

    public User() {
    }

    public User(String userName, String email, char sex, Date birthdate, String geoOrigin, String phoneNumber) {
        this.userName = userName;
        this.email = email;
        this.sex = sex;
        this.birthdate = birthdate;
        this.geoOrigin = geoOrigin;
        this.phoneNumber = phoneNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public char getSex() {
        return sex;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getGeoOrigin() {
        return geoOrigin;
    }

    public void setGeoOrigin(String geoOrigin) {
        this.geoOrigin = geoOrigin;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
