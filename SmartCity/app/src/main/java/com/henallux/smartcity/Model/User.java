package com.henallux.smartcity.Model;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
    @SerializedName("userName")
    private String userName;

    @Nullable
    @SerializedName("password")
    private String password;

    @SerializedName("email")
    private String email;

    @SerializedName("sex")
    private char sex;

    @SerializedName("birthdate")
    private Date birthdate;

    @Nullable
    @SerializedName("geographicOrigins")
    private String geographicOrigins;

    @Nullable
    @SerializedName("phoneNumber")
    private String phoneNumber;

    public User() {
    }

    public User(String userName, String password, String email, Date birthdate, char sex, String GeographicOrigins, String PhoneNumber) {
        setUserName(userName);
        setPassword(password);
        setEmail(email);
        setBirthdate(birthdate);
        setSex(sex);
        setGeographicOrigins(GeographicOrigins);
        setPhoneNumber(PhoneNumber);
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
        if(sex=='M' || sex=='F') {
            this.sex = sex;
        }
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    @Nullable
    public String getGeographicOrigins() {
        return geographicOrigins;
    }

    public void setGeographicOrigins(@Nullable String geographicOrigins) {
        this.geographicOrigins = geographicOrigins;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if(password.length() >= 6) {
            this.password = password;
        }
    }

    @Nullable
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(@Nullable String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
