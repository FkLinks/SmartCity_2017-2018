package com.henallux.smartcity.Model;

public class User {
    private String UserName;
    private String Password;
    private String Email;
    private String Sex;
    private String BirthDate;
    private String GeographicOrigins;

    public User() {
    }

    public User(String userName, String password, String email, String birthDate, String sex, String GeographicOrigins) {
        this.UserName = userName;
        this.Password = password;
        this.Email = email;
        this.BirthDate = birthDate;
        this.Sex = sex;
        this.GeographicOrigins = GeographicOrigins;
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
        return Sex;
    }

    public void setSex(String sex) {
        this.Sex = sex;
    }

    public String getBirthDate() {
        return BirthDate;
    }

    public void setBirthDate(String birthDate) {
        this.BirthDate = birthDate;
    }

    public String getGeographicOrigins() {
        return GeographicOrigins;
    }

    public void setGeographicOrigins(String geographicOrigins) {
        this.GeographicOrigins = geographicOrigins;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        this.Password = password;
    }
}
