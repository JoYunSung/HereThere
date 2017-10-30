package com.pie.herethere;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WValue_ListAdapter extends RecyclerView.Adapter {
    private ArrayList<WValue_ListData> mDataset;

    public WValue_ListAdapter(ArrayList<WValue_ListData>myDataset) {
        mDataset = myDataset;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.wvalue_time)
        TextView tvForeTime;
        @BindView(R.id.wvalue_sky)
        TextView tvForeSky;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.wvalue_custom, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        holder.tvForeTime.setText(mDataset.get(position).time);
        holder.tvForeSky.setText(mDataset.get(position).sky);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}