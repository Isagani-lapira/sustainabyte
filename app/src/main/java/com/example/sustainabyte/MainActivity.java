package com.example.sustainabyte;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private MaterialButton btn_login;
    private TextInputEditText et_email,et_password;
    private TextView tv_signup;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();
        listener();
    }
    //binding views
    private void initialize() {
        tv_signup = findViewById(R.id.tv_signup);
        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);
        btn_login = findViewById(R.id.btn_login);
        mAuth = FirebaseAuth.getInstance();
    }

    private void listener() {
        btn_login.setOnClickListener(v->{
            String email = String.valueOf(et_email.getText());
            String password = String.valueOf(et_password.getText());
            loginAccount(email,password);
        });

        //open registration activity
        tv_signup.setOnClickListener(v->{
            Intent registration = new Intent(this,RegistrationActivity.class);
            startActivity(registration);
        });
    }

    private void loginAccount(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                if (task.isSuccessful()) {
                    // Sign in success, proceed to main page
                    Intent intent = new Intent(this,HomepageActivity.class);
                    startActivity(intent);
                    finish(); //remove the process of login page
                } else {
                    Toast.makeText(MainActivity.this, "Login failed. make sure you check your credentials",
                            Toast.LENGTH_LONG).show();
                }
            });
    }


}