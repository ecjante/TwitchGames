package com.enrico.poweradapter.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * Created by enrico.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {

    private final RecyclerDataSource dataSource;

    public RecyclerAdapter(RecyclerDataSource dataSource, boolean hasStableIds) {
        this.dataSource = dataSource;
        dataSource.attachToAdapter(this);
        setHasStableIds(hasStableIds);
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecyclerViewHolder(parent, dataSource.rendererForType(viewType));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.bind(dataSource.getItem(position));
    }

    @Override
    public int getItemCount() {
        return dataSource.getCount();
    }

    @Override
    public int getItemViewType(int position) {
        return dataSource.viewResourceForPosition(position);
    }

    @Override
    public long getItemId(int position) {
        if (hasStableIds()) {
            return dataSource.getItem(position).getId();
        } else {
            return super.getItemId(position);
        }
    }
}
