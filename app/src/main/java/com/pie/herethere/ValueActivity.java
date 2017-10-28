package com.pie.herethere;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ValueActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView value_back, value_img;
    TextView value_toolbar;

    public void Declaration() {
        value_back = (ImageView) findViewById(R.id.value_back);
        value_back.setOnClickListener(this);

        value_img = (ImageView) findViewById(R.id.value_img);

        value_toolbar = (TextView) findViewById(R.id.value_toolbar);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_value);
        Declaration();

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("jua.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

        Intent intent = getIntent();

        value_toolbar.setText(intent.getStringExtra("title"));
        Glide
                .with(getApplicationContext())
                .load(intent.getStringExtra("img"))
                .into(value_img);
    }

    @Override
    protected void attachBaseContext (Context newBase){
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.value_back) {
            finish();
        }
    }
}