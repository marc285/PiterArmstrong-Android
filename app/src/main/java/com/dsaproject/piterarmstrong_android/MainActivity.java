package com.dsaproject.piterarmstrong_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.dsaproject.piterarmstrong_android.models.User;
import com.dsaproject.piterarmstrong_android.services.UserManagerService;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    //public User userinstance = null;
    private ProgressBar updatebar;
    private UserManagerService usersAPI;

    //---------------------------------------------------------API Methods------------------------------------------------------------//
    public void updateStats(String usrname){
        //Method getUser() of the Users API Interface

        Call<User> call = usersAPI.getUser(usrname);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    //We "fill" the logged User instance
                    User loggedUsr = User.getInstance();
                    loggedUsr.setHealth(response.body().getHealth());
                    loggedUsr.setDefense(response.body().getDefense());
                    loggedUsr.setAttack(response.body().getDefense());
                    loggedUsr.setPieces(response.body().getPieces());
                    loggedUsr.setMoney(response.body().getMoney());

                    //OBJECTS
                }
                else{
                    if(response.code() == 404)
                        Toast.makeText(getApplicationContext(), "Error getting User statistics: " + response.code() + "\n Internal Server Error", Toast.LENGTH_LONG).show();
                }
                showProgress(false);

                BottomNavigationView bottomnNav = findViewById(R.id.bottom_navigation_view);
                bottomnNav.setOnNavigationItemSelectedListener(navListener);

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new DashboardFragment()).commit();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                showProgress(false);

                BottomNavigationView bottomnNav = findViewById(R.id.bottom_navigation_view);
                bottomnNav.setOnNavigationItemSelectedListener(navListener);

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new DashboardFragment()).commit();
            }
        });
    }
    //--------------------------------------------------------------------------------------------------------------------------------//

    public void showProgress (boolean visible){
        //Sets the visibility/invisibility of loginProgressBar
        if(visible)
            this.updatebar.setVisibility(View.VISIBLE);
        else
            this.updatebar.setVisibility(View.GONE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Remove title bar
        this.getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        updatebar = findViewById(R.id.updateProgressBar);

        //Receive the logged user
        Intent intent = getIntent();
        //User usr = (User)intent.getSerializableExtra("loggeduser");

        //Singleton: single instance for the logged user
        //userinstance = User.getInstance();
        /*userinstance.setUsername(usr.getUsername());
        userinstance.setPassword(usr.getPassword());
        userinstance.setHealth(usr.getHealth());
        userinstance.setDefense(usr.getDefense());
        userinstance.setAttack(usr.getAttack());
        userinstance.setPieces(usr.getPieces());
        userinstance.setMoney(usr.getMoney());*/

        Retrofit retrofitinstance = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/dsaApp/") //Later on we will put the server's IP address, meanwhile in localhost
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        usersAPI = retrofitinstance.create(UserManagerService.class);

        updateStats(User.getInstance().getUsername());

        /*BottomNavigationView bottomnNav = findViewById(R.id.bottom_navigation_view);
        bottomnNav.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new DashboardFragment()).commit();*/
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();


        //CERRAR INSTANCIA DEL USER???
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {

                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null;
                    switch (menuItem.getItemId()){
                        case R.id.nav_dashboard:
                            selectedFragment = new DashboardFragment();
                            break;
                        case R.id.nav_users:
                            selectedFragment = new UsersFragment();
                            break;
                        case R.id.nav_settings:
                            selectedFragment = new SettingsFragment();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                    return true;
                }
            };
}
