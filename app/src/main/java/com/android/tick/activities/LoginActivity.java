package com.android.tick.activities;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.android.tick.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginActivity extends AppCompatActivity{

    private AppCompatButton signup;
    private EditText email;
    private EditText pass;
    private TextView fgw;
    private AppCompatButton cont, sign_gg;
    private FirebaseAuth mAuth;

    private String emailUser;


    private SharedPreferences sp;

    private static final String KEY_EMAIL ="email";


    GoogleSignInOptions gso;
    GoogleSignInClient gsc;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email   = findViewById(R.id.email);
        pass    = findViewById(R.id.password);
        cont    = findViewById(R.id.cont_btn);
        fgw     = findViewById(R.id.forgotpass);
        signup  = findViewById(R.id.signup_btn1);
        sign_gg = findViewById(R.id.gg_btn);
        mAuth   = FirebaseAuth.getInstance();

        sp = getSharedPreferences("", MODE_PRIVATE);

        gso     = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc     = GoogleSignIn.getClient(LoginActivity.this, gso);

        GoogleSignInAccount acct =GoogleSignIn.getLastSignedInAccount(this);
        if(acct != null){
            loginUserWithGG();
        }

        sign_gg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        fgw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ForgotPassActivity.class));
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                }
        });

        cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUserWithBtn();
            }
        });
    }

    void signIn(){
        Intent signInIntent = gsc.getSignInIntent();
        startActivityForResult(signInIntent, 1000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1000){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                task.getResult(ApiException.class);
                loginUserWithGG();
            }
            catch (ApiException e){
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Somthing went wrong", Toast.LENGTH_SHORT).show();
            }
        }
    }

    void loginUserWithGG(){
            finish();
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
    }

    private void loginUserWithBtn(){
        String email_login = email.getText().toString().trim();
        String pass_login  = pass.getText().toString().trim();

        if(email_login.isEmpty()) {
            email.setError("Email is required");
            email.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email_login).matches()){
            email.setError("Enter valid email");
            email.requestFocus();
            return;
        }

        if(pass_login.isEmpty()){
            pass.setError("Enter password");
            pass.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(email_login, pass_login).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                   startActivity(new Intent(LoginActivity.this, MainActivity.class));
                   Toast.makeText(LoginActivity.this, "Login success", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(LoginActivity.this, "Login error", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
