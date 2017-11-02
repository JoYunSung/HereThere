package com.pie.herethere;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class Settings_ListAdapter extends BaseAdapter {
    public static class settings_data {
        String name;
        String getName() {
            return name;
        }
        settings_data(String name) {
            this.name = name;
        }
    }

    ArrayList<settings_data>list;
    LayoutInflater inflater;

    Settings_ListAdapter(LayoutInflater inflater, ArrayList<settings_data>list) {
        this.inflater = inflater;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(0);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.settings_custom,null);
        }

        TextView name = (TextView) convertView.findViewById(R.id.setting_name);
        name.setText(list.get(position).getName());

        return convertView;
    }
}
