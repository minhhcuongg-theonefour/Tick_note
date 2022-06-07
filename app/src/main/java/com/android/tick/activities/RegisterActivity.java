package com.android.tick.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.tick.R;
import com.android.tick.entities.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity{

    private FirebaseAuth mAuth;
    private FirebaseDatabase data;
    private DatabaseReference refData;

    private ImageView imageBack;
    private EditText getEmail, getPw, getRpw;
    private AppCompatButton signup2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();
        data = FirebaseDatabase.getInstance();
        refData = data.getReference();

        ImageView imageBack = findViewById(R.id.imageBack1);
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {onBackPressed();}
        });

        getEmail = findViewById(R.id.email_sign_up);
        getPw = findViewById(R.id.pw_sign_up);
        getRpw = findViewById(R.id.re_pw);
        signup2 = findViewById(R.id.layout_sign);

        signup2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                refData = data.getReference("Users");
//                refData.setValue("This is a first time");
                registerUser();
            }
        });
    }

    private void registerUser()
    {
        String email = getEmail.getText().toString().trim();
        String pw = getPw.getText().toString().trim();
        String rpw = getRpw.getText().toString().trim();

        if (email.isEmpty()) {
            getEmail.setError("Email is required");
            getEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            getEmail.setError("Please enter a valid email");
            getEmail.requestFocus();
            return;
        }

        if (pw.isEmpty()) {
            getPw.setError("Enter password");
            getPw.requestFocus();
            return;
        }

        if (pw.length() < 6) {
            getPw.setError("Password require more than 6 characters");
            getPw.requestFocus();
            return;
        }

        if (rpw.isEmpty()) {
            getRpw.setError("Please re-type password");
            getRpw.requestFocus();
            return;
        }

        if (!pw.equals(rpw)) {
            getRpw.setError("Your password are not the same");
            getRpw.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, pw)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(RegisterActivity.this, "Successfully registered", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                        }
                        else{
                            Toast.makeText(RegisterActivity.this, "Failed to register", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}

