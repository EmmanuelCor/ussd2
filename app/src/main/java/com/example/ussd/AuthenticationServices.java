package com.example.ussd;

//import com.google.firebase.FirebaseApp;
//import com.google.firebase.FirebaseOptions;

//import com.google.auth.oauth2.GoogleCredentials;
//import com.google.android.gms.common.ConnectionResult;
//import com.google.android.gms.common.api.GoogleApiClient;
//import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
//import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
//import com.google.android.gms.location.LocationListener;
//import com.google.android.gms.location.LocationRequest;
//import com.google.android.gms.location.LocationServices;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.atomic.AtomicBoolean;

public class AuthenticationServices {

    private FirebaseAuth mAuth;
//    FileInputStream serviceAccount =
//            new FileInputStream("./serviceAccountKey.json");
//
//    FirebaseOptions options = new FirebaseOptions.Builder()
//            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
//            .build();
//
//FirebaseApp.initializeApp(options);

    public AuthenticationServices() {
        mAuth = FirebaseAuth.getInstance();
    }

    public boolean isLoggedIn() {
        return mAuth.getCurrentUser() != null;
    }

    public boolean createAccount(String email, String password) {
        // [START create_user_with_email]
        AtomicBoolean status = new AtomicBoolean(false);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("", "createUserWithEmail:success");
                        status.set(true);
//                        FirebaseUser user = mAuth.getCurrentUser();
                    } else {
                        status.set(false);

                        // If sign in fails, display a message to the user.
                        Log.w("", "createUserWithEmail:failure", task.getException());
                    }
                });
        // [END create_user_with_email]
        return status.get();
    }

    public void signIn(String email, String password) {
        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("", "signInWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("", "signInWithEmail:failure", task.getException());
//                            Toast.makeText(EmailPasswordActivity.this, "Authentication failed.",
//                                    Toast.LENGTH_SHORT).show();
                    }
                });
        // [END sign_in_with_email]
    }

    public boolean signOut(){
//        mAuth.getCurrentUser().
        FirebaseAuth.getInstance().signOut();
        return true;
    }

}
