package com.example.sustainabyte;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends AppCompatActivity {

    private MaterialButton btn_register;
    private TextInputEditText et_email,et_password,et_confirm_password;
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
        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);
        et_confirm_password = findViewById(R.id.et_confirm_password);
        btn_register = findViewById(R.id.btn_register);
        tv_signin = findViewById(R.id.tv_signin);
        mAuth = FirebaseAuth.getInstance();
    }
    private void listener() {
        btn_register.setOnClickListener(v->{
            String email = String.valueOf(et_email.getText()).trim();
            String password = String.valueOf(et_password.getText()).trim();
            String confirmPass = String.valueOf(et_confirm_password.getText()).trim();

            //check for password is match
            if(password.equals(confirmPass)) // register account
                registerAccount(email,password);
            else
                Toast.makeText(this, "Password does not matched", Toast.LENGTH_LONG).show();

        });

        //go back to login
        tv_signin.setOnClickListener(v->{
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        });
    }

    private void registerAccount(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this,task -> {
                    if(task.isSuccessful()){ //created successfully
                        Toast.makeText(this, "Account successfully created", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(this,MainActivity.class);
                        startActivity(intent); //go to login activity
                    }else{
                        Toast.makeText(this, "Error: "+
                                        task.getException().getMessage().toString(),
                                        Toast.LENGTH_SHORT).show();
                    }
                });
    }

}