package com.example.sustainabyte;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegistrationActivity extends AppCompatActivity {

    private MaterialButton btn_register;
    private TextInputEditText et_email,et_username, et_password,et_confirm_password;
    private TextView tv_signin;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        initialize();
        listener();
    }

    private void initialize() {
        et_username = findViewById(R.id.et_username);
        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);
        et_confirm_password = findViewById(R.id.et_confirm_password);
        btn_register = findViewById(R.id.btn_register);
        tv_signin = findViewById(R.id.tv_signin);
        mAuth = FirebaseAuth.getInstance();
    }
    private void listener() {
        btn_register.setOnClickListener(v->{
            String username = String.valueOf(et_username.getText()).trim();
            String email = String.valueOf(et_email.getText()).trim();
            String password = String.valueOf(et_password.getText()).trim();
            String confirmPass = String.valueOf(et_confirm_password.getText()).trim();

            //check for password is match
            if(password.equals(confirmPass)) // register account
                registerAccount(username, email,password);
            else
                Toast.makeText(this, "Password does not matched", Toast.LENGTH_LONG).show();

        });

        //go back to login
        tv_signin.setOnClickListener(v->{
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        });
    }

    private void registerAccount(String username, String email, String password) {
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this,task -> {
                    if(task.isSuccessful()){ //created successfully

                        // Get the current user's unique ID
                        String userId = mAuth.getCurrentUser().getUid();

                        // Create a reference to the Firebase Realtime Database with your database URL
                        DatabaseReference currentUserRef = FirebaseDatabase.getInstance()
                                .getReference().child("Users").child(userId);

                        // Create a HashMap to store the user's data
                        HashMap<String, Object> userData = new HashMap<>();
                        userData.put("username", username);
                        userData.put("email", email);

                        // Set the user's data in the database
                        currentUserRef.setValue(userData)
                                .addOnCompleteListener(task1 -> {
                                    if (task1.isSuccessful()) {
                                        Toast.makeText(this, "Account successfully created", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(this,MainActivity.class);
                                        startActivity(intent); //go to login activity
                                    } else {
                                        Toast.makeText(this, "Error: "+ task1.getException().getMessage().toString(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }else{
                        Toast.makeText(this, "Error: "+
                                        task.getException().getMessage().toString(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }



}