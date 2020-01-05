package com.dsaproject.piterarmstrong_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.dsaproject.piterarmstrong_android.models.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    public User userinstance = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Remove title bar
        this.getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        //Receive the logged user
        Intent intent = getIntent();
        User usr = (User)intent.getSerializableExtra("loggeduser");

        //Singleton: single instance for the logged user
        userinstance = User.getInstance();
        userinstance.setUsername(usr.getUsername());
        userinstance.setPassword(usr.getPassword());
        userinstance.setHealth(usr.getHealth());
        userinstance.setDefense(usr.getDefense());
        userinstance.setAttack(usr.getAttack());
        userinstance.setPieces(usr.getPieces());
        userinstance.setMoney(usr.getMoney());

        BottomNavigationView bottomnNav = findViewById(R.id.bottom_navigation_view);
        bottomnNav.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new DashboardFragment(userinstance)).commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        //Singleton: Close the instance
        userinstance.closeInstance();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {

                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null;
                    switch (menuItem.getItemId()){
                        case R.id.nav_dashboard:
                            selectedFragment = new DashboardFragment(userinstance);
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
