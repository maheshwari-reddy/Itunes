package com.developer.maheshwari.itunes.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.developer.maheshwari.itunes.R;


public class HomeScreenActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        int SPLASH_TIME_OUT = 3500;
        new Handler().postDelayed(new Runnable()
        {
            public void run()
            {
                    Intent i = new Intent(HomeScreenActivity.this, ListingActivity.class);
                    startActivity(i);
                    finish();
            }
        }, SPLASH_TIME_OUT);
    }
}