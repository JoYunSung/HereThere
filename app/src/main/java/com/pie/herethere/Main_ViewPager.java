package com.pie.herethere;

import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class Main_ViewPager extends PagerAdapter {

    LayoutInflater inflater;
    public Integer imgRe[] = new Integer[] {
            R.drawable.card1_0,
            R.drawable.card1_0,
            R.drawable.card1_0,
    };

    public Main_ViewPager(LayoutInflater inflater) {
        this.inflater = inflater;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        View view = null;
        view = inflater.inflate(R.layout.main_viewpager, null);

        ImageView img = (ImageView) view.findViewById(R.id.main_viewPager_img);
        img.setImageResource(imgRe[position]);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (position) {
                    case 0 :
                        Intent intent = new Intent(container.getContext(), AnyWhereActivity.class);
                        container.getContext().startActivity(intent);
                        break;
                    case 1 :
                        break;
                    case 2 :
                        break;
                }
            }
        });

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public float getPageWidth(int position) {
        return (0.87f);
    }
}