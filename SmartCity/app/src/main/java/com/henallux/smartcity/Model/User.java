package com.henallux.smartcity.Model;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
    @SerializedName("userName")
    private String UserName;

    @SerializedName("password")
    private String Password;

    @SerializedName("email")
    private String Email;

    @SerializedName("sex")
    private char Sex;

    @SerializedName("birtdate")
    private Date Birthdate;

    @Nullable
    @SerializedName("geographicOrigins")
    private String GeographicOrigins;

    @Nullable
    @SerializedName("phoneNumber")
    private String PhoneNumber;

    public User() {
    }

    public User(String userName, String password, String email, Date Birthdate, char sex, String GeographicOrigins, String PhoneNumber) {
        setUserName(userName);
        setPassword(password);
        setEmail(email);
        setBirthdate(Birthdate);
        setSex(sex);
        setGeographicOrigins(GeographicOrigins);
        setPhoneNumber(PhoneNumber);
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

    public char getSex() {
        return Sex;
    }

    public void setSex(char sex) {
        if(sex=='M' || sex=='F') {
            this.Sex = sex;
        }
    }

    public Date getBirthdate() {
        return Birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.Birthdate = birthdate;
    }

    @Nullable
    public String getGeographicOrigins() {
        return GeographicOrigins;
    }

    public void setGeographicOrigins(@Nullable String geographicOrigins) {
        this.GeographicOrigins = geographicOrigins;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        if(password.length() >= 6) {
            this.Password = password;
        }
    }

    @Nullable
    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(@Nullable String phoneNumber) {
        PhoneNumber = phoneNumber;
    }
}
