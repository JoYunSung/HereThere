package com.pie.herethere;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class WValue_ListAdapter extends BaseAdapter {
    ArrayList<WValue_ListData> list;
    LayoutInflater inflater;

    public WValue_ListAdapter(LayoutInflater inflater, ArrayList<WValue_ListData>list) {
        this.list = list;
        this.inflater = inflater;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i).sky;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.wvalue_custom,null);
        }
        TextView time = (TextView) convertView.findViewById(R.id.wvalue_time);
        time.setText(list.get(position).getTime());

        TextView sky = (TextView) convertView.findViewById(R.id.wvalue_sky);
        sky.setText(list.get(position).getSky());

        return convertView;
    }
}