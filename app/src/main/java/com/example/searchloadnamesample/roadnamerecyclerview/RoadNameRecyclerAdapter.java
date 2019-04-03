package com.example.searchloadnamesample.roadnamerecyclerview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.searchloadnamesample.R;

import java.util.ArrayList;

public abstract class RoadNameRecyclerAdapter extends RecyclerView.Adapter<RoadNameRecyclerAdapter.ItemViewHolder> {
    private ArrayList<RoadName> items;
    private Context itemContext;

    public RoadNameRecyclerAdapter(ArrayList<RoadName> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        itemContext = viewGroup.getContext();
        View view = LayoutInflater.from(itemContext).inflate(R.layout.item_road, viewGroup, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, int i) {
        itemViewHolder.tvRoadAddr.setText(items.get(i).getRoadAddr());
        itemViewHolder.tvJibunAddr.setText(items.get(i).getJibunAddr());

        itemViewHolder.itemView.setOnClickListener(v -> setItemClickListener(items.get(i).getRoadAddr()));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public abstract void setItemClickListener(String s);

    class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView tvRoadAddr;
        private TextView tvJibunAddr;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRoadAddr = itemView.findViewById(R.id.tvRoadAddr);
            tvJibunAddr = itemView.findViewById(R.id.tvJibunAddr);
        }
    }
}
