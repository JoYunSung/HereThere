package com.pie.herethere;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //1초후 메인화면으로 넘어감
        new Timer().schedule(new TimerTask() {
            public void run() {
                Intent ite_main = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(ite_main);
                finish();
            }
        }, 2000);
    }
}