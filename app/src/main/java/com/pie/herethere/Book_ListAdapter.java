package com.pie.herethere;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class Book_ListAdapter extends BaseAdapter {
    ArrayList<Book_ListData> list;
    LayoutInflater inflater;

    public Book_ListAdapter(LayoutInflater inflater, ArrayList<Book_ListData>list) {
        this.list = list;
        this.inflater = inflater;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i).title;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.book_custom,null);
        }
        TextView title = (TextView) convertView.findViewById(R.id.book_list_title);
        title.setText(list.get(position).getTitle());

        return convertView;
    }
}