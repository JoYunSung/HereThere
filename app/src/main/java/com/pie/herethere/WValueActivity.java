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

public class WValueActivity extends AppCompatActivity implements View.OnClickListener {

    String Title, Img, ContentId;

    ImageView BackImage, BackGroundImg;
    TextView ToolbarText;

    public void Declaration() {
        BackImage = (ImageView) findViewById(R.id.wvalue_back);
        BackImage.setOnClickListener(this);

        BackGroundImg = (ImageView) findViewById(R.id.wvalue_img);

        ToolbarText = (TextView) findViewById(R.id.wvalue_toolbar);
    }

    public void GetData() {
        Intent intent = getIntent();

        Img = intent.getStringExtra("img");
        Glide
                .with(getApplicationContext())
                .load(Img)
                .into(BackGroundImg);

        Title = intent.getStringExtra("title");
        ToolbarText.setText(Title);

        ContentId = intent.getStringExtra("id");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wvalue);
        Declaration();
        SetFont();
        GetData();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.wvalue_back) {
            finish();
        }
    }

    public void SetFont() {
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("jua.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}