package com.codepath.tule.simpletodo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder>{

    public interface OnLongClickLister {
        void onItemLongClicked(int pos);
    }
    List<String> items;
    OnLongClickLister onLongClickLister;

    public ItemAdapter(List<String> items, OnLongClickLister onLongClickLister) {
        this.items = items;
        this.onLongClickLister = onLongClickLister;
    }
    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View todoView = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_1, parent, false);
        return new ItemViewHolder(todoView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        // grab the item
        String item = items.get(position);

        // bind the item to view holder
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
    }

    // Container to provide easy access to view that represent each row of the list
    class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView tvItem;

        public ItemViewHolder (@NonNull View itemView) {
            super(itemView);
            tvItem = itemView.findViewById(android.R.id.text1);
        }

        public void bind(String item) {
            tvItem.setText(item);
            tvItem.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onLongClickLister.onItemLongClicked(getAdapterPosition());
                    return true;
                }
            });
        }
    }
}
