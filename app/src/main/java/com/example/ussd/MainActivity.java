package com.example.ussd;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity{
    private RecyclerView recyclerViewSentUSSDs;
    private RecyclerView recyclerViewPendingUSSDs;

    private static final int SMS_PERMISSION_REQUEST_CODE = 100;
    private BroadcastReceiver smsReceiver;
    private List<String> Sent;
    private List<String> pending;
    private FirestoreHelper firestoreHelper = new FirestoreHelper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CardView sentBtn = findViewById(R.id.recyclerViewSentUSSDs);
        CardView pendingBtn = findViewById(R.id.pendingUssds);
        CardView logoutBtn = findViewById(R.id.cardLogout);

//        Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.ujuzicraft.credo_pesa");
//        if (launchIntent != null) {
//            startActivity(launchIntent);
//        } else {
//            Log.d("Yoo", "Not available");
//        }

        if(FirebaseAuth.getInstance().getCurrentUser() == null) {
            Intent intent = new Intent(this, LoginPage.class);
            startActivity(intent);
            finish();
        }

        sentBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, SentUssd.class);
            startActivity(intent);
        });

        pendingBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, SentUssd.class);
            startActivity(intent);
        });

        logoutBtn.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(this, LoginPage.class);
            startActivity(intent);
            finish();
        });

        Sent = new ArrayList<>();
        pending = new ArrayList<>();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECEIVE_SMS}, SMS_PERMISSION_REQUEST_CODE);
            } else {
                startSmsListener();
            }
        } else {
            startSmsListener();
        }
    }

    private void startSmsListener() {
        smsReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Bundle bundle = intent.getExtras();
                if (bundle != null) {
                    Object[] pdus = (Object[]) bundle.get("pdus");
                    if (pdus != null) {
                        for (Object pdu : pdus) {
                            SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdu);
                            String messageBody = smsMessage.getMessageBody();
                            String sender = smsMessage.getOriginatingAddress();

                            // Assuming the payment message contains the amount and client number
                            double amountPaid = extractAmount(messageBody);
                            String clientNumber = extractClientNumber(messageBody);

                            // Run USSD based on the amount paid
                            String ussdCode = "";
                            if (amountPaid == 5) {
                                ussdCode = "*188*6*1*2*" + clientNumber + "*1*1#";
                            } else if (amountPaid == 20) {
                                ussdCode = "*180*5*2*" + clientNumber + "*6*1#";
                            } else if (amountPaid == 55) {
                                ussdCode = "*180*5*2*" + clientNumber + "*8*1#";
                            }

                            // Add USSD to pending list
                            pending.add(ussdCode);

                            firestoreHelper.postData(messageBody, ussdCode);

                            // Execute USSD code
                            runUSSD(ussdCode);
                        }
                    }
                }
            }
        };

        IntentFilter intentFilter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
        registerReceiver(smsReceiver, intentFilter);
    }

    private double extractAmount(String messageBody) {
        // Implement logic to extract the amount paid from the message body
        // This might involve parsing the message to find specific patterns
        return 5; // Placeholder value, replace with actual logic
    }

    private String extractClientNumber(String messageBody) {
        // Implement logic to extract the client number from the message body
        // This might involve parsing the message to find specific patterns
        return "PPPP"; // Placeholder value, replace with actual logic
    }

    private void runUSSD(String ussdCode) {
        // Implement code to run the USSD code
        // This might involve using Intent to start a service or activity to dial the USSD code
        // Assume the USSD code execution is successful
        Sent.add(ussdCode);
        pending.remove(ussdCode);
        Toast.makeText(MainActivity.this, "Running USSD: " + ussdCode, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == SMS_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startSmsListener();
            } else {
                Toast.makeText(this, "SMS permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (smsReceiver != null) {
            unregisterReceiver(smsReceiver);
        }
    }
}
