package com.pie.herethere;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class Open_ListAdapter extends BaseAdapter {

    LayoutInflater inflater;
    ArrayList<open_data>list;

    public Open_ListAdapter(LayoutInflater inflater, ArrayList<open_data>list) {
        this.inflater = inflater;
        this.list = list;
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
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.open_custom,null);
        }
        TextView title = (TextView) convertView.findViewById(R.id.open_title);
        title.setText(list.get(position).getTitle());

        TextView addr = (TextView) convertView.findViewById(R.id.open_addr);
        addr.setText(list.get(position).getAddr());

        TextView text = (TextView) convertView.findViewById(R.id.open_text);
        text.setText(list.get(position).getText());

        return convertView;
    }

    public static class open_data {
        String title;
        String addr;
        String text;

        String getTitle() {
            return title;
        }
        String getAddr() {
            return addr;
        }
        String getText() {
            return text;
        }

        open_data(String title, String addr, String text) {
            this.title = title;
            this.addr = addr;
            this.text = text;
        }
    }
}