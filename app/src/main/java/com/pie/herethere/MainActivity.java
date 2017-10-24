package com.pie.herethere;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    ViewPager viewPager;
    ImageView img_1, img_2, img_3;

    LinearLayout layout;

    public void Declaration() {
        viewPager = (ViewPager) findViewById(R.id.main_viewPager);

        img_1 = (ImageView) findViewById(R.id.main_circle_1);
        img_2 = (ImageView) findViewById(R.id.main_circle_2);
        img_3 = (ImageView) findViewById(R.id.main_circle_3);

        layout = (LinearLayout) findViewById(R.id.main_li);
    }

    public void Ready() {
        DisplayMetrics dm = getApplicationContext().getResources().getDisplayMetrics();
        int height = dm.heightPixels;

        // 레이아웃 크기 - (이미지 크기 변경)
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) layout.getLayoutParams();
        layoutParams.height = height;

        layout.setLayoutParams(layoutParams);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Declaration();
        Ready();

        // 뷰 페이저
        Main_ViewPager main_viewPager = new Main_ViewPager(getLayoutInflater());
        viewPager.setAdapter(main_viewPager);
        viewPager.setOnPageChangeListener(this);
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
}