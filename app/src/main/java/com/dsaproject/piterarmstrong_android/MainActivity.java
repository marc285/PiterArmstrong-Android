package com.dsaproject.piterarmstrong_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.dsaproject.piterarmstrong_android.models.Objeto;
import com.dsaproject.piterarmstrong_android.models.ObjetoList;
import com.dsaproject.piterarmstrong_android.models.User;
import com.dsaproject.piterarmstrong_android.services.UserManagerService;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomnNav;
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
                    loggedUsr.setPassword(response.body().getPassword());
                    Log.i("MyActivity", "your current password is:" + User.getInstance().getPassword());
                    loggedUsr.setHealth(response.body().getHealth());
                    loggedUsr.setDefense(response.body().getDefense());
                    loggedUsr.setAttack(response.body().getAttack());
                    loggedUsr.setPieces(response.body().getPieces());
                    loggedUsr.setMoney(response.body().getMoney());
                    loggedUsr.setScreen(response.body().getScreen());
                }
                else
                    Toast.makeText(getApplicationContext(), "Error getting User statistics: " + response.code() + "\nInternal Server Error", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        Call<List<Objeto>> call2 = usersAPI.getUserObjects(usrname);
        call2.enqueue(new Callback<List<Objeto>>() {
            @Override
            public void onResponse(Call<List<Objeto>> call, Response<List<Objeto>> response) {
                if(response.isSuccessful()) {
                    //We "fill" the logged User Object List (instance)
                    ObjetoList loggedUsrList = ObjetoList.getInstance();
                    loggedUsrList.setList(response.body());
                }
                else
                    Toast.makeText(getApplicationContext(), "Error getting User Object List: " + response.code() + "\nInternal Server Error", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<List<Objeto>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
    //--------------------------------------------------------------------------------------------------------------------------------//

    public void closeActivity(){
        this.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Remove title bar
        this.getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        //updatebar = findViewById(R.id.updateProgressBar);

        Retrofit retrofitinstance = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/dsaApp/") //Later on we will put the server's IP address, meanwhile in localhost
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        usersAPI = retrofitinstance.create(UserManagerService.class);

        //showProgress(true);
        updateStats(User.getInstance().getUsername());

        bottomnNav = findViewById(R.id.bottom_navigation_view);
        bottomnNav.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new DashboardFragment()).commit();

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
