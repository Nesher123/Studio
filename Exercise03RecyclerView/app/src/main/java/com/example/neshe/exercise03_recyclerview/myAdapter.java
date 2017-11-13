package com.example.neshe.exercise03_recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by neshe on 11/11/2017.
 */

public class myAdapter extends RecyclerView.Adapter{
    private String[] mDataSet;

    public myAdapter(String[] data) {
        mDataSet = data;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;

        public ViewHolder(TextView view) {
            super(view);
            mTextView = view;
        }
    }

    @Override
    public myAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TextView view = new TextView(parent.getContext());
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder)holder).mTextView.setText(mDataSet[position]);
        ((ViewHolder)holder).mTextView.setTextSize(40);
    }

    @Override
    public int getItemCount() {
        return mDataSet.length;
    }
}
