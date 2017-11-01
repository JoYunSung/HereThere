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

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {

    ViewPager viewPager;
    ImageView img_1, img_2, img_3;

    ImageView main_img1, main_img2;

    LinearLayout layout;
    int height;

    ImageView Search_img, Main_book;
    boolean isClick_BackButtonOK = false;

    public void Declaration() {
        viewPager = (ViewPager) findViewById(R.id.main_viewPager);

        img_1 = (ImageView) findViewById(R.id.main_circle_1);
        img_2 = (ImageView) findViewById(R.id.main_circle_2);
        img_3 = (ImageView) findViewById(R.id.main_circle_3);

        main_img1 = (ImageView) findViewById(R.id.main_img_1);
        main_img1.setOnClickListener(this);
        main_img2 = (ImageView) findViewById(R.id.main_img_2);
        main_img2.setOnClickListener(this);

        Search_img = (ImageView) findViewById(R.id.main_search);
        Search_img.setOnClickListener(this);

        Main_book = (ImageView) findViewById(R.id.main_book);
        Main_book.setOnClickListener(this);

        layout = (LinearLayout) findViewById(R.id.main_li);
    }

    public void Ready() {
        DisplayMetrics dm = getApplicationContext().getResources().getDisplayMetrics();
        height = dm.heightPixels;

        // 레이아웃 크기 - (이미지 크기 변경)
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) layout.getLayoutParams();
        layoutParams.height = height;

        layout.setLayoutParams(layoutParams);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("jua.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Declaration();
        Ready();

        // 뷰 페이저
        final Main_ViewPager main_viewPager = new Main_ViewPager(getLayoutInflater());
        viewPager.setAdapter(main_viewPager);
        viewPager.setOnPageChangeListener(this);

        viewPager.setClipToPadding(false);
        viewPager.setPageMargin(0);

        viewPager.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override public void transformPage(View page, float position) {
                // 1
                if (viewPager.getCurrentItem() == 0) {
                    page.setTranslationX(height/24);
                }
                // 3
                else if (viewPager.getCurrentItem() == main_viewPager.getCount() - 1) {
                    page.setTranslationX(-(height/32));
                }
                // 2
                else {
                    page.setTranslationX(height/23);
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        // 검색
        if (view.getId() == R.id.main_search) {
            Intent intent = new Intent(MainActivity.this, SearchActivity.class);
            startActivity(intent);
        }

        // 오늘 날씨 어때?
        else if (view.getId() == R.id.main_img_1) {
            Intent intent = new Intent(MainActivity.this, WeatherActivity.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }

        // 뭐 먹을까?
        else if (view.getId() == R.id.main_img_2) {
            Intent intent = new Intent(MainActivity.this, DataActivity.class);
            intent.putExtra("type", "eat");
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }

        // 북마크
        else if (view.getId() == R.id.main_book) {
            Intent intent = new Intent(MainActivity.this, BookMarkActivity.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    // 뷰 페이저 인디케이터
    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0 :
                img_1.setImageResource(R.drawable.circle_1);
                img_2.setImageResource(R.drawable.circle_2);
                img_3.setImageResource(R.drawable.circle_2);
                break;
            case 1 :
                img_1.setImageResource(R.drawable.circle_2);
                img_2.setImageResource(R.drawable.circle_1);
                img_3.setImageResource(R.drawable.circle_2);
                break;
            case 2 :
                img_1.setImageResource(R.drawable.circle_2);
                img_2.setImageResource(R.drawable.circle_2);
                img_3.setImageResource(R.drawable.circle_1);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    protected void attachBaseContext (Context newBase){
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
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