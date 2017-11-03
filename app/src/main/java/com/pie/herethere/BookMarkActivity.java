package com.pie.herethere;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class BookMarkActivity extends AppCompatActivity implements View.OnClickListener {

    ListView listView;
    ArrayList<Book_ListData> list = new ArrayList<>();
    Book_ListAdapter adapter;

    ImageView bar_bt1, bar_bt2, bar_bt4;
    TextView er;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_mark);

        listView = (ListView) findViewById(R.id.book_list);

        adapter = new Book_ListAdapter(getLayoutInflater(), list);
        listView.setAdapter(adapter);

        bar_bt1 = (ImageView) findViewById(R.id.bar_bt1);
        bar_bt1.setOnClickListener(this);
        bar_bt2 = (ImageView) findViewById(R.id.bar_bt2);
        bar_bt2.setOnClickListener(this);
        bar_bt4 = (ImageView) findViewById(R.id.bar_bt4);
        bar_bt4.setOnClickListener(this);

        er = (TextView) findViewById(R.id.book_er);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(BookMarkActivity.this, ValueActivity.class);
                intent.putExtra("title", list.get(i).getTitle());
                intent.putExtra("img", list.get(i).getImg());
                intent.putExtra("id", list.get(i).getContentId());
                startActivity(intent);
                overridePendingTransition(R.anim.anim_right, R.anim.anim_hold);
            }
        });

        TimerTask adTast = new TimerTask() {
            public void run() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                getFile();
                            }
                        });
                    }
                }).start();
            }
        };
        Timer timer = new Timer();
        timer.schedule(adTast, 0, 1000);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.bar_bt1) {
            Intent intent = new Intent(BookMarkActivity.this, MainActivity.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
        }

        if (view.getId() == R.id.bar_bt2) {
            Intent intent = new Intent(BookMarkActivity.this, SearchActivity.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
        }

        if (view.getId() == R.id.bar_bt4) {
            Intent intent = new Intent(BookMarkActivity.this, SettingsActivity.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
        }
    }

    public void getFile() {
        try {
            File directory = new File("/data/data/com.pie.herethere/files");
            File[] files = directory.listFiles();

            list.clear();

            for (int i = 0; i < files.length; i++) {
                try {
                    String fileName = files[i].getName();
                    String sp[] = fileName.split(" ");

                    FileInputStream fis = openFileInput(fileName);
                    BufferedReader br = new BufferedReader(new InputStreamReader(fis, "UTF-8"));

                    String title = br.readLine();
                    String img = br.readLine();

                    if (!title.equals("해제")) {
                        list.add(new Book_ListData(title, img, sp[1]));
                    }

                    br.close();
                    fis.close();
                } catch (Exception e) { }
            }
            adapter.notifyDataSetChanged();
        } catch (Exception e) { }

        if (list.size() != 0) {
            er.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(BookMarkActivity.this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }
}