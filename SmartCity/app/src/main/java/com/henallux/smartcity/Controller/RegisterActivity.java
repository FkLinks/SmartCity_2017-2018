package com.henallux.smartcity.Controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.henallux.smartcity.DAO.UserDAO;
import com.henallux.smartcity.Exceptions.LoginUserException;
import com.henallux.smartcity.Exceptions.RegisterUserException;
import com.henallux.smartcity.Model.TokenReceived;
import com.henallux.smartcity.Model.User;
import com.henallux.smartcity.R;
import com.henallux.smartcity.Validation;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.Date;

public class RegisterActivity extends AppCompatActivity {

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private ConnectivityManager connectivityManager;
    private NetworkInfo activeNetwork;
    private boolean isConnected;
    private Button register;
    private TextView goToLogin;
    private EditText userName;
    private EditText password;
    private EditText email;
    private RadioGroup sex;
    private RadioButton male;
    private RadioButton female;
    private EditText birthdate;
    private EditText geographicalOrigins;
    private EditText phoneNumber;
    private UserDAO userDAO = new UserDAO();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        preferences= PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();

        connectivityManager = (ConnectivityManager) RegisterActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);

        userName = (EditText) findViewById(R.id.login);
        password = (EditText) findViewById(R.id.password);
        email = (EditText) findViewById(R.id.email);
        sex = (RadioGroup) findViewById(R.id.sexChoice);
        sex.check(R.id.male);
        male = (RadioButton) findViewById(R.id.male);
        female = (RadioButton) findViewById(R.id.female);
        birthdate = (EditText) findViewById(R.id.birthDate);
        geographicalOrigins = (EditText) findViewById(R.id.whereHeFrom);
        phoneNumber = (EditText) findViewById(R.id.phoneNumber);

        register = (Button)findViewById(R.id.register);
        register.setOnClickListener(registerBtnListener);

        goToLogin = (TextView) findViewById(R.id.sign_in);
        goToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
    }

    private View.OnClickListener registerBtnListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            User user;
            activeNetwork = connectivityManager.getActiveNetworkInfo();
            isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
            if(checkValidation()) {
                if(isConnected) {

                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    Date birthDate = new Date();
                    try {
                        birthDate = df.parse(birthdate.getText().toString());
                    }
                    catch (ParseException pe){

                    }
                    user = new User(userName.getText().toString(),
                            password.getText().toString(),
                            email.getText().toString(),
                            birthDate,
                            ((sex.getCheckedRadioButtonId() == R.id.male) ? 'M' : 'F'),
                            (geographicalOrigins.getText().toString().equals("")) ? null : geographicalOrigins.getText().toString(),
                            (phoneNumber.getText().toString().equals("")) ? null : phoneNumber.getText().toString());

                    new SubmitRegistration().execute(user);
                }
                else{
                    Toast.makeText(RegisterActivity.this, R.string.errorMissInternetCo, Toast.LENGTH_LONG).show();
                }
            }
            else{
                Toast.makeText(RegisterActivity.this, R.string.errorsInFormEncountered, Toast.LENGTH_LONG).show();
            }
        }
    };

    private boolean checkValidation(){
        boolean valid = true;

        //affichera toutes les erreurs
        if(!Validation.isValid(email, getString(R.string.email_regex), getString(R.string.errorMsgFormatEmail), getString(R.string.RequiredForm), true))
            valid = false;

        if(!Validation.isValid(birthdate, getString(R.string.birthdate_regex), getString(R.string.errorMsgFormatBirthdate), getString(R.string.RequiredForm), true))
            valid = false;

        if(!Validation.hasText(userName, getString(R.string.RequiredForm)))
            valid = false;

        if(!Validation.isValid(password, getString(R.string.password_regex), getString(R.string.errorMsgFormatPassword), getString(R.string.RequiredForm), true))
            valid = false;

        if(!Validation.isValid(geographicalOrigins, getString(R.string.geographical_origins_regex), getString(R.string.errorMsgFormatGO), getString(R.string.RequiredForm), false))
            valid = false;

        if(!Validation.isValid(phoneNumber, getString(R.string.regex_phoneNumber), getString(R.string.error_format_phoneNumber), getString(R.string.RequiredForm), false))
            valid = false;

        return valid;
    }

    private class SubmitRegistration extends AsyncTask<User, Void, TokenReceived> {
        @Override
        protected TokenReceived doInBackground(User... userParams) {

            TokenReceived tokenReceived = new TokenReceived();
            try {
                tokenReceived.setCode(userDAO.registerUser(userParams[0]));

            }
            catch (RegisterUserException e) {
                tokenReceived.setErrorException(getString(R.string.error_registering_user));
            }
            catch (JSONException e) {
                tokenReceived.setErrorException(getString(R.string.json_exception_encountered));
            }
            return tokenReceived;
        }

        @Override
        protected void onPostExecute(TokenReceived tokenReceived) {
            super.onPostExecute(tokenReceived);
            JSONObject toSend = new JSONObject();

            TokenReceived tokenReceivedForLog;

            switch (tokenReceived.getCode()){
                case 200:
                    try {
                        toSend.put("UserName", userName.getText());
                        toSend.put("Password", password.getText());

                        tokenReceivedForLog = userDAO.checkUserExist(toSend.toString());

                        if(tokenReceivedForLog.getCode() == 200){
                            Intent home = new Intent(RegisterActivity.this, HomeActivity.class);
                            editor.putString("token", tokenReceivedForLog.getToken());
                            editor.putString("userName", userName.getText().toString());
                            editor.commit();
                            startActivity(home);

                            Toast.makeText(RegisterActivity.this, R.string.registerSuccessful, Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(RegisterActivity.this, R.string.connectionError, Toast.LENGTH_LONG).show();
                        }

                    }
                    catch (LoginUserException e){
                        Toast.makeText(RegisterActivity.this, getString(R.string.errorCheckingUser), Toast.LENGTH_LONG).show();
                    }
                    catch (JSONException e) {
                        Toast.makeText(RegisterActivity.this, R.string.json_exception_encountered, Toast.LENGTH_LONG).show();
                    }
                    break;
                case 400:
                    Toast.makeText(RegisterActivity.this, R.string.error_registering_user, Toast.LENGTH_LONG).show();
                    //Toast.makeText(RegisterActivity.this, R.string.error_register_mail_unique, Toast.LENGTH_LONG).show();
                    break;
                case 408:
                    Toast.makeText(RegisterActivity.this, R.string.expired_request, Toast.LENGTH_LONG).show();
                    break;
                case 409:
                    Toast.makeText(RegisterActivity.this, R.string.error_register_username_unique, Toast.LENGTH_LONG).show();
                    break;
                case 500:
                    Toast.makeText(RegisterActivity.this, R.string.error_server_encountered, Toast.LENGTH_LONG).show();
                    break;
                default:
                    Toast.makeText(RegisterActivity.this, tokenReceived.getErrorException(), Toast.LENGTH_LONG).show();
                    break;
            }
        }
    }
}
