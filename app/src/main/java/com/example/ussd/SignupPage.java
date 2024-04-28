package com.example.ussd;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;

import java.io.FileNotFoundException;
import java.util.Timer;
import java.util.TimerTask;

public class SignupPage extends AppCompatActivity {
    EditText username;
    EditText password;
    EditText passwordRepeat;

    TextView errorText;

    Button signUpBtn;
    Button toLogin;

    AuthenticationServices authenticationServices = new AuthenticationServices();

    public SignupPage() throws FileNotFoundException {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        username = findViewById(R.id.username_input);
        password = findViewById(R.id.password_input);
        passwordRepeat = findViewById(R.id.password_input_repeat);

        signUpBtn = findViewById(R.id.signup_button);
        toLogin = findViewById(R.id.to_login_button);

        errorText = findViewById(R.id.error_text);

        signUpBtn.setOnClickListener(v -> {
            if(username.getText().length() == 0 || password.getText().length() == 0) {
                errorText.setText("All fields need to be filled");
            } else {
                if(!passwordRepeat.getText().toString().equals(password.getText().toString())) {
                    errorText.setText("Passwords do not match");
                } else {
                    boolean signedIn = authenticationServices.createAccount(username.getText().toString(), password.getText().toString());
                    if(signedIn) {
                        Intent intent = new Intent(this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        errorText.setText("Error logging in");
                    }
                }
            }
        });

        toLogin.setOnClickListener(v -> {
            Intent intent = new Intent(this, LoginPage.class);
            startActivity(intent);
        });
//Declare the timer
        Timer t = new Timer();
//Set the schedule function and rate
        t.scheduleAtFixedRate(new TimerTask() {

                                  @Override
                                  public void run() {

                                      if(FirebaseAuth.getInstance().getCurrentUser() != null) {
                                          Intent intent = new Intent(SignupPage.this, MainActivity.class);
                                          startActivity(intent);
                                          finish();
                                          t.cancel();
                                      }

                                      //Called each time when 1000 milliseconds (1 second) (the period parameter)
                                  }

                              },
//Set how long before to start calling the TimerTask (in milliseconds)
                0,
//Set the amount of time between each execution (in milliseconds)
                1000);
    }
}