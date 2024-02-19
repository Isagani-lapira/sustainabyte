package com.example.sustainabyte;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class AboutUsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        initialize();
    }

    private void initialize() {
        //back button on toolbar
        findViewById(R.id.backBtn).setOnClickListener(v -> finish());
    }
}