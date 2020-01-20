package com.dsaproject.piterarmstrong_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.dsaproject.piterarmstrong_android.models.User;
import com.dsaproject.piterarmstrong_android.services.UserManagerService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.text.InputType.TYPE_NULL;

public class LoginActivity extends AppCompatActivity {

    //----------------Attributes-----------------//
    private EditText usrtxt;
    private EditText pwdtxt;
    private Button loginbtn;
    private Button registerbtn;
    private ProgressBar loginbar;

    private UserManagerService usersAPI;

    private Boolean authenticated; //Provisional
    //-------------------------------------------//


    //---------------------------------------------------------API Methods------------------------------------------------------------//
    public void login(User usr){
        //Method login() of the Users API Interface

        Call<User> call = usersAPI.login(usr);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){

                    //We "fill" the logged User instance
                    User loggedUsr = User.getInstance();
                    loggedUsr.setUsername(response.body().getUsername());
                    loggedUsr.setPassword(response.body().getPassword());
                    //The other parameters are set when doing getUser() when opening a new activity

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish(); //-----Response activity (close session - delete SHAREDPREFERENCES)
                }
                else{
                    if(response.code() == 404)
                        Toast.makeText(getApplicationContext(), "Authentication error: " + response.code() + "\nInvalid username or password" , Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(getApplicationContext(), "Authentication error: " + response.code() + "\nInternal Server Error", Toast.LENGTH_LONG).show();
                }
                showProgress(false);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                showProgress(false);
            }
        });
    }

    public void register(final User usr){
        //Method register() of the Users API Interface

        Call<Void> call = usersAPI.register(usr);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){

                    //We "fill" the logged User instance
                    User loggedUsr = User.getInstance();
                    loggedUsr.setUsername(usr.getUsername());
                    loggedUsr.setPassword(usr.getPassword());

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish(); //-----Response activity (close session - delete SHAREDPREFERENCES)
                }
                else{
                    if(response.code() == 400)
                        Toast.makeText(getApplicationContext(), "Register error: " + response.code() + "\nBad Request (Error in parameters' format)" , Toast.LENGTH_LONG).show();
                    else if(response.code() == 409)
                        Toast.makeText(getApplicationContext(), "Register error: " + response.code() + "\nAlready existing User" , Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(getApplicationContext(), "Register error: " + response.code() + "\nInternal Server Error", Toast.LENGTH_LONG).show();
                }
                showProgress(false);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                showProgress(false);
            }
        });
    }
    //--------------------------------------------------------------------------------------------------------------------------------//


    //-----------------------------------------------------Activity Methods-----------------------------------------------------------//
    public void showProgress (boolean visible){
        //Sets the visibility/invisibility of loginProgressBar
        if(visible)
            this.loginbar.setVisibility(View.VISIBLE);
        else
            this.loginbar.setVisibility(View.GONE);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Remove title bar
        this.getSupportActionBar().hide();
        setContentView(R.layout.activity_login);

        usrtxt = findViewById(R.id.userEditText);
        pwdtxt = findViewById(R.id.passwordEditText);
        loginbtn = findViewById(R.id.loginButton);
        registerbtn = findViewById(R.id.registerButton);
        loginbar = findViewById(R.id.loginProgressBar);

        showProgress(false);

        //Provisional
        authenticated = false;

        Retrofit retrofitinstance = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/dsaApp/") //Later on we will put the server's IP address, meanwhile in localhost
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        usersAPI = retrofitinstance.create(UserManagerService.class);

        //SharedPreferences sharedPrefs = getSharedPreferences("MySharedPreferences", MODE_APPEND);

        if(authenticated){
            // "invisible" Login to the backend's API REST
            //Then we expect a User/null, if we get a null we display that there has been a modification in the user authenticated in the app

            //Block EditTexts so that the login is with the user already authenticated in the app
            //usrtxt.setInputType(TYPE_NULL);
            //pwdtxt.setInputType(TYPE_NULL);

            showProgress(true);


            //login with the user stored in SHARED PREFERENCES
        }

        //"else"
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgress(true);

                if(usrtxt.getText().toString().equals("") || pwdtxt.getText().toString().equals("")) {
                    showProgress(false);
                    Toast.makeText(getApplicationContext(), "Error: you must fill User Name and Password fields", Toast.LENGTH_LONG).show();
                }
                else{
                    login(new User(usrtxt.getText().toString(), pwdtxt.getText().toString(), 0, 0, 0, 0, 0, 0));
                }
            }
        });
        //
        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgress(true);

                if(usrtxt.getText().toString().equals("") || pwdtxt.getText().toString().equals("")) {
                    showProgress(false);
                    Toast.makeText(getApplicationContext(), "Error: you must fill User Name and Password fields", Toast.LENGTH_LONG).show();
                }
                else{
                    register(new User(usrtxt.getText().toString(), pwdtxt.getText().toString(), 0, 0, 0, 0, 0, 0));
                }
            }
        });
    }
    //---------------------------------------------------------------------------------------------------------------------------------//
}
