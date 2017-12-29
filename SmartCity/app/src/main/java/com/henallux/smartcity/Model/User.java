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
        setUserName(userName);
        setPassword(password);
        setEmail(email);
        setBirthDate(birthDate);
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
        return Sex;
    }

    public void setSex(String sex) {
        if(sex.equals("M") || sex.equals("F")) {
            this.Sex = sex;
        }
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
        if(password.length() >= 6) {
            this.Password = password;
        }
    }
}
