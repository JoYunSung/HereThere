package com.pie.herethere;

import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class Value_ViewPager extends PagerAdapter {

    LayoutInflater inflater;
    ArrayList<Value_Data> list;

    public Value_ViewPager(LayoutInflater inflater, ArrayList<Value_Data>list) {
        this.inflater = inflater;
        this.list = list;
    }


    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        View view = null;
        view = inflater.inflate(R.layout.main_viewpager, null);

        ImageView img = (ImageView) view.findViewById(R.id.main_viewPager_img);

        Glide
                .with(container.getContext())
                .load(list.get(position).getImg().toString())
                .into(img);

        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return list.size();
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
        return (0.9f);
    }
}