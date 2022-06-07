package com.android.tick.activities;

import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.android.tick.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassActivity extends AppCompatActivity {

    private AppCompatButton resetPass;
    private EditText emailReset;
    FirebaseAuth auth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pw);

        resetPass   = findViewById(R.id.reset_pw_btn);
        emailReset  = findViewById(R.id.reset_pw_email);
        auth        = FirebaseAuth.getInstance();

        resetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(auth);
                resetPassWord();
            }
        });



    }

    private void resetPassWord(){
        String email_pw = emailReset.getText().toString().trim();

        if(email_pw.isEmpty()) {
            emailReset.setError("Email is required");
            emailReset.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email_pw).matches()){
            emailReset.setError("Enter valid email");
            emailReset.requestFocus();
            return;
        }

        auth.sendPasswordResetEmail(email_pw).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                System.out.println("Co action");


                if(task.isSuccessful()){
                    Toast.makeText(ForgotPassActivity.this, "Check your email to reset password.", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(ForgotPassActivity.this, "Try again! Something went wrong.", Toast.LENGTH_LONG).show();
                }
            }
        }).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                System.out.println("successed");
            }
        });
    }
}
