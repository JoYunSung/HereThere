package com.pie.herethere;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class Result_ListAdapter extends RecyclerView.Adapter<Result_ListAdapter.ViewHolder> {

    private ArrayList<Result_ListData> list;

    public Result_ListAdapter(ArrayList<Result_ListData> list) {
        this.list = list;
    }

    @Override
    public Result_ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.result_custom, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(Result_ListAdapter.ViewHolder holder, int position) {
        holder.title.setText(list.get(position).getTitle());

        Glide
                .with(holder.itemView.getContext())
                .load(list.get(position).getImg().toString())
                .into(holder.img);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView title;

        public ViewHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.result_list_img);
            title = (TextView) itemView.findViewById(R.id.result_list_title);
        }
    }
}
