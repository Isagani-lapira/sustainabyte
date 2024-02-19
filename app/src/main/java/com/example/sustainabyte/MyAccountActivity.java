package com.example.sustainabyte;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.BitSet;

public class MyAccountActivity extends AppCompatActivity {

    private TextView profileTextView, profileTextView2;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

        //back button on toolbar
        findViewById(R.id.backBtn).setOnClickListener(v -> finish());

        // Initialize TextView
        profileTextView = findViewById(R.id.username);
        profileTextView2 = findViewById(R.id.email);

        // Access cached user data
        String username = UserDataManager.getInstance().getUsername();
        String email = UserDataManager.getInstance().getEmail();


        profileTextView.setText("@"+username);
        profileTextView2.setText(email);

    }



}
