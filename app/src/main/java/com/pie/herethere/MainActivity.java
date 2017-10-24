package com.pie.herethere;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    ViewPager viewPager;
    ImageView img_1, img_2, img_3;

    public void Declaration() {
        viewPager = (ViewPager) findViewById(R.id.main_viewPager);

        img_1 = (ImageView) findViewById(R.id.main_circle_1);
        img_2 = (ImageView) findViewById(R.id.main_circle_2);
        img_3 = (ImageView) findViewById(R.id.main_circle_3);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Declaration();

        Main_ViewPager main_viewPager = new Main_ViewPager(getLayoutInflater());
        viewPager.setAdapter(main_viewPager);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

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
            public void onPageScrollStateChanged(int state) {}
        });
    }
}