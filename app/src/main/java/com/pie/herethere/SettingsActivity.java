package com.pie.herethere;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView bar_bt1, bar_bt2, bar_bt3;

    ArrayList<Settings_ListAdapter.settings_data>list = new ArrayList<>();
    Settings_ListAdapter adapter;

    ListView listView;
    String ver;

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

        PackageInfo pInfo = null;
        try {
            pInfo = getPackageManager().getPackageInfo(this.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        final String versionName = pInfo.versionName;
        ver = versionName;


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    // 앱 버전
                    case 0 :
                        AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
                        builder.setTitle("앱 버전");
                        builder.setMessage("현재 앱 버전은 " + ver + " 입니다.");

                        AlertDialog dialog = builder.create();
                        dialog.show();
                        break;

                    // 오픈소스 라이선스
                    case 1 :
                        Intent intent = new Intent(SettingsActivity.this, OpenSourceActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.anim_right, R.anim.anim_hold);
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
    public void onBackPressed() {
        Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }
}
