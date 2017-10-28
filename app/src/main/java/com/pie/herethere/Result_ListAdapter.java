package com.pie.herethere;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class Result_ListAdapter extends BaseAdapter{
    ArrayList<Result_ListData>list;
    LayoutInflater inflater;

    public Result_ListAdapter(LayoutInflater inflater, ArrayList<Result_ListData>list) {
        this.list = list;
        this.inflater = inflater;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.result_custom, null);
        }

        ImageView img = (ImageView) convertView.findViewById(R.id.result_list_img);
        TextView title = (TextView) convertView.findViewById(R.id.result_list_title);

        Glide
                .with(convertView.getContext())
                .load(list.get(position).getImg().toString())
                .into(img);

        title.setText(list.get(position).getTitle());

        return convertView;
    }
}
