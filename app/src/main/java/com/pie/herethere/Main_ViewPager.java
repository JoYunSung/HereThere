package com.pie.herethere;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class Main_ViewPager extends PagerAdapter {

    LayoutInflater inflater;
    public Integer imgRe[] = new Integer[] {
            R.drawable.red,
            R.drawable.yello,
            R.drawable.green
    };

    public Main_ViewPager(LayoutInflater inflater) {
        this.inflater = inflater;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = null;
        view = inflater.inflate(R.layout.main_viewpager, null);

        ImageView img = (ImageView) view.findViewById(R.id.main_viewPager_img);
        img.setImageResource(imgRe[position]);

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
}