package com.example.ussd;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class ItemCardViewHolder
        extends RecyclerView.ViewHolder {
    TextView sentAmount;
    TextView examMessage;
    TextView sentNum;
    View view;

    ItemCardViewHolder(View itemView)
    {
        super(itemView);
        sentAmount
                = (TextView)itemView
                .findViewById(R.id.sentAmount);
        sentNum
                = (TextView)itemView
                .findViewById(R.id.senderNumber);
//        view  = itemView;
    }
}
