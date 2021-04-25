package com.project.fridgeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.project.fridgeapp.reviewProducts.ReviewActivity;

import java.util.Objects;

public class WelcomeActivity extends AppCompatActivity {

    private static final int TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        ActivityStarter starter = new ActivityStarter();
        starter.start();
    }

    private class ActivityStarter extends Thread {

        @Override
        public void run() {
            try {
                Thread.sleep(TIME_OUT);
            } catch (Exception e) {
                Log.e("Splash Screen", Objects.requireNonNull(e.getMessage()));
            }
            Intent intent = new Intent(WelcomeActivity.this, ReviewActivity.class);
            WelcomeActivity.this.startActivity(intent);
            WelcomeActivity.this.finish();
        }
    }
}