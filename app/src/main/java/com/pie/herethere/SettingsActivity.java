package com.pie.herethere;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView bar_bt1, bar_bt2, bar_bt3;

    ArrayList<Settings_ListAdapter.settings_data>list = new ArrayList<>();
    Settings_ListAdapter adapter;

    ListView listView;

    public void Declaration() {
        bar_bt1 = (ImageView)findViewById(R.id.bar_bt1);
        bar_bt2 = (ImageView)findViewById(R.id.bar_bt2);
        bar_bt3 = (ImageView)findViewById(R.id.bar_bt3);

        bar_bt1.setOnClickListener(this);
        bar_bt2.setOnClickListener(this);
        bar_bt3.setOnClickListener(this);

        listView = (ListView) findViewById(R.id.setting_list);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Declaration();

        list.add(new Settings_ListAdapter.settings_data("앱 버전"));
        list.add(new Settings_ListAdapter.settings_data("오픈소스 라이선스"));
        adapter = new Settings_ListAdapter(getLayoutInflater(), list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    // 앱 버전
                    case 0 :
                        break;

                    // 오픈소스 라이선스
                    case 1 :
                        break;
                }
            }
        });
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
            Intent intent = new Intent(SettingsActivity.this, SearchActivity.class);
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
