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

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {

    ViewPager viewPager;
    ImageView img_1, img_2, img_3;

    LinearLayout layout;
    int height;

    ImageView Search_img;

    public void Declaration() {
        viewPager = (ViewPager) findViewById(R.id.main_viewPager);

        img_1 = (ImageView) findViewById(R.id.main_circle_1);
        img_2 = (ImageView) findViewById(R.id.main_circle_2);
        img_3 = (ImageView) findViewById(R.id.main_circle_3);

        Search_img = (ImageView) findViewById(R.id.main_search);
        Search_img.setOnClickListener(this);

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
        if (view.getId() == R.id.main_search) {
            Intent intent = new Intent(MainActivity.this, SearchActivity.class);
            startActivity(intent);
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
}