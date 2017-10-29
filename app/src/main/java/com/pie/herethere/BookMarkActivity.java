package com.pie.herethere;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class BookMarkActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<Book_ListData> list = new ArrayList<>();
    Book_ListAdapter adapter;

    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_mark);

        listView = (ListView) findViewById(R.id.book_list);
        back = (ImageView) findViewById(R.id.book_back);

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("jua.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

        TimerTask adTast = new TimerTask() {
            public void run() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    list.clear();
                                    getFile();
                                } catch (Exception e) { }
                            }
                        });
                    }
                }).start();
            }
        };
        Timer timer = new Timer();
        timer.schedule(adTast, 0, 3000);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(BookMarkActivity.this, ValueActivity.class);
                intent.putExtra("title", list.get(i).getTitle());
                intent.putExtra("img", list.get(i).getImg());
                intent.putExtra("id", list.get(i).getContentId());
                startActivity(intent);
            }
        });
    }

    public void getFile() {
        File directory = new File("/data/data/com.pie.herethere/files");
        File[] files = directory.listFiles();

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
        adapter = new Book_ListAdapter(getLayoutInflater(), list);
        listView.setAdapter(adapter);
    }

    @Override
    protected void attachBaseContext (Context newBase){
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}