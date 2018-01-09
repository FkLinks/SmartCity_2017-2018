package com.henallux.smartcity.DAO;

import com.henallux.smartcity.Exceptions.LoginUserException;
import com.henallux.smartcity.Exceptions.RegisterUserException;
import com.henallux.smartcity.Exceptions.ShowInfosUserException;
import com.henallux.smartcity.Model.TokenReceived;
import com.henallux.smartcity.Model.User;
import com.henallux.smartcity.Model.UserLogin;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public interface UserDAO {
    TokenReceived checkUserExist(UserLogin login_password) throws LoginUserException, JSONException;

    Date getExpirationDateForToken (JSONObject tokenReceived)throws JSONException;

    int registerUser(User user) throws RegisterUserException, JSONException;

    User getUserByName(String userName, String token) throws ShowInfosUserException, JSONException;
}
