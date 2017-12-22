package com.henallux.smartcity;

import android.content.Intent;
import android.content.SharedPreferences;
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
import com.henallux.smartcity.Model.TokenReceived;
import com.henallux.smartcity.Model.User;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
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
    private UserDAO userDAO = new UserDAO();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        preferences= PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();

        userName = (EditText) findViewById(R.id.login);
        password = (EditText) findViewById(R.id.password);
        email = (EditText) findViewById(R.id.email);
        sex = (RadioGroup) findViewById(R.id.sexChoice);
        sex.check(R.id.male);
        male = (RadioButton) findViewById(R.id.male);
        female = (RadioButton) findViewById(R.id.female);
        birthdate = (EditText) findViewById(R.id.birthDate);
        geographicalOrigins = (EditText) findViewById(R.id.whereHeFrom);

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
            JSONObject toSend = new JSONObject();
            if(checkValidation()) {
                try {
                    toSend.put("UserName", userName.getText());
                    toSend.put("Password", password.getText());
                    toSend.put("Email", email.getText());
                    toSend.put("BirthDate", birthdate.getText());
                    toSend.put("Sex", ((sex.getCheckedRadioButtonId() == R.id.male)? "M" : "F"));
                    toSend.put("GeographicOrigins", geographicalOrigins.getText());

                    new SubmitRegistration().execute(toSend.toString());
                }
                catch (Exception e) {
                    Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
                /*User user = new User(userName.getText().toString(),
                        password.getText().toString(),
                        email.getText().toString(),
                        birthdate.getText().toString(),
                        ((sex.getCheckedRadioButtonId() == R.id.male)? "M" : "F"),
                        geographicalOrigins.getText().toString());*/

            }
            else{
                Toast.makeText(RegisterActivity.this, R.string.errorsInFormEncountered, Toast.LENGTH_LONG).show();
            }
        }
    };

    private boolean checkValidation(){
        boolean valid = true;

        //affichera toutes les erreurs
        if(!Validation.isEmailAdressValid(email, getString(R.string.email_regex), getString(R.string.errorMsgFormatEmail), getString(R.string.RequiredForm), true))
            valid = false;

        if(!Validation.isBirthdateValid(birthdate, getString(R.string.birthdate_regex), getString(R.string.errorMsgFormatBirthdate), getString(R.string.RequiredForm), true))
            valid = false;

        if(!Validation.hasText(userName, getString(R.string.RequiredForm)))
            valid = false;

        if(!Validation.isPasswordValid(password, getString(R.string.password_regex), getString(R.string.errorMsgFormatPassword), getString(R.string.RequiredForm), true))
            valid = false;

        return valid;
    }

    private class SubmitRegistration extends AsyncTask<String, Void, TokenReceived> {
        @Override
        protected TokenReceived doInBackground(String... userParams) {
            TokenReceived tokenReceived = new TokenReceived();
            try {
                tokenReceived = userDAO.registerUser(userParams[0]);

            }
            catch (Exception e) {
                Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
            return tokenReceived;
        }

        @Override
        protected void onPostExecute(TokenReceived tokenReceived) {
            super.onPostExecute(tokenReceived);
            JSONObject toSend = new JSONObject();

            if(tokenReceived.getCode() == 200){

                try {
                    Toast.makeText(RegisterActivity.this, R.string.registerSuccessful, Toast.LENGTH_LONG).show();

                    toSend.put("UserName", userName.getText());
                    toSend.put("Password", password.getText());

                    new CheckUser().execute(toSend.toString());

                } catch (Exception e) {
                    Toast.makeText(RegisterActivity.this, R.string.connectionError, Toast.LENGTH_LONG).show();
                }
                /*Intent home = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(home);
                Toast.makeText(RegisterActivity.this, R.string.registerSuccessful, Toast.LENGTH_LONG).show();*/
            }
            else{
                Toast.makeText(RegisterActivity.this, R.string.registerFailed, Toast.LENGTH_LONG).show();
            }
        }

        private class CheckUser extends AsyncTask<String, Void, TokenReceived> {
            @Override
            protected TokenReceived doInBackground(String... userParams) {
                TokenReceived tokenReceived = new TokenReceived();

                try {
                    tokenReceived = userDAO.checkUserExist(userParams[0]);

                }
                catch (Exception e) {
                    Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
                return tokenReceived;
            }

            @Override
            protected void onPostExecute(TokenReceived tokenReceived) {
                super.onPostExecute(tokenReceived);

                if(tokenReceived.getCode() == 200){
                    Intent home = new Intent(RegisterActivity.this, HomeActivity.class);
                    editor.putString("token", tokenReceived.getToken());
                    editor.putString("userName", userName.getText().toString());
                    editor.commit();
                    startActivity(home);
                }
                else{
                    Toast.makeText(RegisterActivity.this, R.string.registerFailed, Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
