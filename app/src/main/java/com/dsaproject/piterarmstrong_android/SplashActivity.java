package com.dsaproject.piterarmstrong_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Remove title bar
        this.getSupportActionBar().hide();
        setContentView(R.layout.activity_splash);

        final Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Open the Login Activity after 2 seconds
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        },2000);
    }
}
