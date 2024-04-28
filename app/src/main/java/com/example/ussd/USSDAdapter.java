package com.example.ussd;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class USSDAdapter extends RecyclerView.Adapter<USSDAdapter.USSDViewHolder> {

    private List<String> ussdList;
    private OnItemClickListener listener; // Interface for item click listener

    // Constructor to initialize USSDAdapter with data and item click listener
    public USSDAdapter(List<String> ussdList, OnItemClickListener listener) {
        this.ussdList = ussdList;
        this.listener = listener;
    }

    // Interface for item click listener
    public interface OnItemClickListener {
        void onItemClick(String ussd);
    }

    @NonNull
    @Override
    public USSDViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ussd, parent, false);
        return new USSDViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull USSDViewHolder holder, int position) {
        String ussd = ussdList.get(position);
        holder.textViewUSSD.setText(ussd);

        // Set click listener for item
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(ussd);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return ussdList != null ? ussdList.size() : 0;
    }
    static class USSDViewHolder extends RecyclerView.ViewHolder {
        TextView textViewUSSD;

        public USSDViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewUSSD = itemView.findViewById(R.id.textViewUSSD);
        }
    }
}
