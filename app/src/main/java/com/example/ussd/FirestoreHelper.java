package com.example.ussd;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirestoreHelper {
    String TAG = "";
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public void postData(String message, String ussd) {

        // Create a new user with a first and last name
        Map<String, Object> user = new HashMap<>();
        user.put("message", message);
        user.put("ussd", ussd);

// Add a new document with a generated ID
        db.collection("ussds")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }

    public List<SentData> readData() {
        final List<SentData> dataList = new ArrayList<>();

        db.collection("ussds")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String number = document.getString("ussd");
                                String amount = document.getString("message");
                                Log.d(TAG, "Data list: " + amount);

                                SentData data = new SentData(number, amount);
                                dataList.add(data);
                            }
                            // Return the data list here after all documents have been processed
                            Log.d(TAG, "Data list: " + dataList);
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });

        // Return the empty data list immediately, before the documents have been processed
        return dataList;
    }
}
