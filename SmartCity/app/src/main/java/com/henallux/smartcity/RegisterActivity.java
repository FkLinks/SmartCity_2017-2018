package com.henallux.smartcity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.henallux.smartcity.DAO.UserDAO;
import com.henallux.smartcity.Model.TokenReceived;

import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    private Boolean login;
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
        male = (RadioButton) findViewById(R.id.male);
        male.setSelected(true);
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
            if(checkValidation()) submitRegistration();
        }
    };

    private boolean checkValidation(){
        boolean valid = true;

        if(!Validation.isEmailAdressValid(email, getString(R.string.errorMsgFormatEmail), getString(R.string.RequiredForm), true)
                || !Validation.isBirthdateValid(birthdate, getString(R.string.errorMsgFormatBirthdate), getString(R.string.RequiredForm), true)
                || !Validation.hasText(userName, getString(R.string.RequiredForm))
                || !Validation.isPasswordValid(password, getString(R.string.errorMsgFormatPassword), getString(R.string.RequiredForm), true)){

            valid = false;
        }

        return valid;
    }

    private void submitRegistration(){
        JSONObject toSend = new JSONObject();
        TokenReceived tokenReceived = new TokenReceived();
        try {
            toSend.put("UserName", userName.getText());
            toSend.put("Password", password.getText());
            toSend.put("Email", email.getText());
            toSend.put("Sex", ((sex.getCheckedRadioButtonId()==0)?"M":"F"));
            toSend.put("Birthdate", birthdate.getText());
            toSend.put("GeographicOrigins", geographicalOrigins.getText());

            tokenReceived = userDAO.registerUser(toSend.toString(), tokenReceived);

            if(tokenReceived.getCode() == 200){
                Intent home = new Intent(RegisterActivity.this, HomeActivity.class);
                editor.putBoolean("login", true);
                editor.commit();
                startActivity(home);
            }
            else{
                Toast.makeText(RegisterActivity.this, "Mauvais numero ou mdp", Toast.LENGTH_LONG).show();
            }
        }
        catch (Exception e){

        }
    }
}
