package com.pie.herethere;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView bar_bt1, bar_bt2, bar_bt3;

    public void Declaration() {
        bar_bt1 = (ImageView)findViewById(R.id.bar_bt1);
        bar_bt2 = (ImageView)findViewById(R.id.bar_bt2);
        bar_bt3 = (ImageView)findViewById(R.id.bar_bt3);

        bar_bt1.setOnClickListener(this);
        bar_bt2.setOnClickListener(this);
        bar_bt3.setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Declaration();
    }

    @Override
    public void onClick(View view) {
        //하단 바 이미지 1
        if (view.getId() == R.id.bar_bt1) {
            Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
        }

        //하단 바 이미지 2
        if (view.getId() == R.id.bar_bt2) {
            Intent intent = new Intent(SettingsActivity.this, BookMarkActivity.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
        }

        //하단 바 이미지 3
        if (view.getId() == R.id.bar_bt3) {
            Intent intent = new Intent(SettingsActivity.this, BookMarkActivity.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
        }
    }

    @Override
    protected void attachBaseContext (Context newBase){
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }
}