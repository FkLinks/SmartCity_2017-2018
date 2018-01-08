package com.henallux.smartcity.Controller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.henallux.smartcity.DAO.UserDAO;
import com.henallux.smartcity.Exceptions.ShowInfosUserException;
import com.henallux.smartcity.Model.User;
import com.henallux.smartcity.R;

import org.json.JSONException;

public class UserProfileActivity extends AppCompatActivity {

    private String token;
    private String userName;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private TextView userNameProfile;
    private TextView sexProfile;
    private TextView emailProfile;
    private TextView originCountryProfile;
    private TextView birthDateProfile;
    private String error = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        token = preferences.getString("token", "");
        userName = preferences.getString("userName", "");
        editor=preferences.edit();

        userNameProfile = (TextView) findViewById(R.id.userName);
        sexProfile = (TextView) findViewById(R.id.sex);
        emailProfile = (TextView) findViewById(R.id.emailAdrUser);
        originCountryProfile = (TextView) findViewById(R.id.originCountry);
        birthDateProfile = (TextView) findViewById(R.id.birthDateUser);

        new LoadUser().execute();
    }

    private class LoadUser extends AsyncTask<String, Void, User> {
        @Override
        protected User doInBackground(String... strings) {
            UserDAO userDAO = new UserDAO();
            User user = new User();
            try{
                user = userDAO.getUserByName(userName, token);
            }
            catch (ShowInfosUserException e){
                error = getString(R.string.error_user_data_exception);
            }
            catch (JSONException e){
                error = getString(R.string.json_exception_encountered);
            }

            return user;
        }

        @Override
        protected void onPostExecute(User user) {
            super.onPostExecute(user);

            if(error.equals("")) {
                userNameProfile.setText(user.getUserName());
                sexProfile.setText(""+user.getSex());
                emailProfile.setText(user.getEmail());
                originCountryProfile.setText(user.getGeographicOrigins());

                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                birthDateProfile.setText(df.format(user.getBirthdate()));
            }
            else{
                Toast.makeText(UserProfileActivity.this, error, Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!token.equals(""))
            getMenuInflater().inflate(R.menu.menu_profile_page_sign_out, menu);
        else
            getMenuInflater().inflate(R.menu.menu_profile_page_sign_in, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId())
        {
            case R.id.sign_in:
                startActivity(new Intent(UserProfileActivity.this, LoginActivity.class));
                return true;
            case R.id.sign_out:
                editor.putString("token", "");
                editor.commit();
                startActivity(new Intent(UserProfileActivity.this, MainActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
