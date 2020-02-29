package com.example.jnuelibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity {

    EditText EmailIDEt, PasswordEt;
    ProgressBar progressBar;

    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EmailIDEt = findViewById(R.id.EmailIDEt);
        PasswordEt = findViewById(R.id.PasswordEt);
        progressBar = findViewById(R.id.Progressbar);

        fAuth = FirebaseAuth.getInstance();

        if(fAuth.getCurrentUser() != null){
            Intent intent = new Intent(login.this, MainActivity.class);
            startActivity(intent);
        }
    }

    public void Forget_password(View view) {
        Toast.makeText(this, "Service under development", Toast.LENGTH_SHORT).show();
    }

    public void Register_now(View view) {
        Intent intent = new Intent(login.this, Registration.class);
        startActivity(intent);
    }

    public void Login(View view) {
        String email = EmailIDEt.getText().toString().trim();
        String password = PasswordEt.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            EmailIDEt.setError("required");
            return;
        }
        if(TextUtils.isEmpty(password)){
            PasswordEt.setError("required");
            return;
        }
        if(password.length()<6){
            PasswordEt.setError("password must be greater than 5 character");
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(login.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Toast.makeText(login.this, "Error!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }

        });



    }
}
