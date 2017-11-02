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

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    int height;

    ImageView img1, img2, img3, bar_bt1, bar_bt2, bar_bt3, bar_bt4;
    boolean isClick_BackButtonOK = false;

    public void Declaration() {
        img1 = (ImageView) findViewById(R.id.main_img_1);
        img1.setOnClickListener(this);
        img2 = (ImageView) findViewById(R.id.main_img_2);
        img2.setOnClickListener(this);
        img3 = (ImageView) findViewById(R.id.main_img_3);
        img3.setOnClickListener(this);

        bar_bt1 = (ImageView) findViewById(R.id.bar_bt1);
        bar_bt1.setOnClickListener(this);
        bar_bt2 = (ImageView) findViewById(R.id.bar_bt2);
        bar_bt2.setOnClickListener(this);
        bar_bt3 = (ImageView) findViewById(R.id.bar_bt3);
        bar_bt3.setOnClickListener(this);
        bar_bt4 = (ImageView) findViewById(R.id.bar_bt4);
        bar_bt4.setOnClickListener(this);
    }

    public void Ready() {
        DisplayMetrics dm = getApplicationContext().getResources().getDisplayMetrics();
        height = dm.heightPixels;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Declaration();
        Ready();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.main_img_1) {
            Intent intent = new Intent(MainActivity.this, DataActivity.class);
            intent.putExtra("type", "where");
            startActivity(intent);
            overridePendingTransition(R.anim.anim_right, R.anim.anim_hold);
        }

        if (view.getId() == R.id.main_img_2) {
            Intent intent = new Intent(MainActivity.this, DataActivity.class);
            intent.putExtra("type", "eat");
            startActivity(intent);
            overridePendingTransition(R.anim.anim_right, R.anim.anim_hold);
        }

        if (view.getId() == R.id.main_img_3) {
            Intent intent = new Intent(MainActivity.this, WeatherActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.anim_right, R.anim.anim_hold);
        }

        if (view.getId() == R.id.bar_bt2) {
            Intent intent = new Intent(MainActivity.this, SearchActivity.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
        }

        if (view.getId() == R.id.bar_bt3) {
            Intent intent = new Intent(MainActivity.this, BookMarkActivity.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
        }

        if (view.getId() == R.id.bar_bt4) {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        if (!isClick_BackButtonOK) {
            isClick_BackButtonOK = true;
            new Timer().schedule(new TimerTask() {
                public void run() {
                    isClick_BackButtonOK = false;
                }
            }, 2000);
            Toast.makeText(this, back_String, Toast.LENGTH_SHORT).show();
        } else {
            finish();
        }
    }
    public String back_String = "뒤로 버튼을 한번 더 누르시면 종료됩니다.";
}