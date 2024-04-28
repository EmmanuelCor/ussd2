package com.example.ussd;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SentUssd extends AppCompatActivity{

    RecyclerView recyclerView;
    ClickListiner listiner;
    SentAdapter sentAdapter;
    FirestoreHelper firestoreHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sent_ussd);

        firestoreHelper = new FirestoreHelper();

//        List<SentData> list = getData();
        List<SentData> list = firestoreHelper.readData();

        recyclerView = findViewById(R.id.sentRecyclerView);

        listiner = index -> {
            Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show();
        };

        sentAdapter = new SentAdapter(list, this, listiner);
        recyclerView.setAdapter(sentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    // Sample data for RecyclerView
    private List<SentData> getData()
    {
        List<SentData> list = new ArrayList<>();
        list.add(new SentData("0706560454",
                "55"));
        list.add(new SentData("0702015937",
                "20"));
        list.add(new SentData("0714951041",
                "20"));
        list.add(new SentData("0706560454",
                "55"));
        list.add(new SentData("0702015937",
                "20"));
        list.add(new SentData("0714951041",
                "20"));
        list.add(new SentData("0706560454",
                "55"));
        list.add(new SentData("0702015937",
                "20"));
        list.add(new SentData("0714951041",
                "20"));

        return list;
    }
}
