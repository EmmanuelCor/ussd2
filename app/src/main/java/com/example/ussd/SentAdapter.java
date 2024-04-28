package com.example.ussd;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

public class SentAdapter
        extends RecyclerView.Adapter<ItemCardViewHolder> {

    List<SentData> list;

    Context context;

    ClickListiner listiner;

    public SentAdapter(List<SentData> list,
                                Context context,ClickListiner listiner)
    {
        this.list = list;
        this.context = context;
        this.listiner = listiner;
    }

    @Override
    public ItemCardViewHolder
    onCreateViewHolder(ViewGroup parent,
                       int viewType)
    {

        Context context
                = parent.getContext();
        LayoutInflater inflater
                = LayoutInflater.from(context);

        // Inflate the layout

        View photoView
                = inflater
                .inflate(R.layout.entry_card,
                        parent, false);

        ItemCardViewHolder viewHolder
                = new ItemCardViewHolder(photoView);
        return viewHolder;
    }

    @Override
    public void
    onBindViewHolder(final ItemCardViewHolder viewHolder,
                     final int position)
    {
        final int index = viewHolder.getAdapterPosition();
        viewHolder.sentAmount
                .setText(list.get(position).amount);
        viewHolder.sentNum
                .setText(list.get(position).number);
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }

    @Override
    public void onAttachedToRecyclerView(
            RecyclerView recyclerView)
    {
        super.onAttachedToRecyclerView(recyclerView);
    }


}