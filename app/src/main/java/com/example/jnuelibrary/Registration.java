package com.example.jnuelibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Registration extends AppCompatActivity {

    EditText nameET, studentIDET, emailIDET, passwordET, contactET;
    ProgressBar progressBar;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        nameET = findViewById(R.id.NameET);
        studentIDET = findViewById(R.id.StudentIDET);
        emailIDET = findViewById(R.id.EmailET);
        passwordET = findViewById(R.id.PasswordET);
        contactET = findViewById(R.id.ContactET);

        progressBar = findViewById(R.id.Progressbar);

        fAuth = FirebaseAuth.getInstance();

    }

    public void Submit(View view) {

        if (nameET.getText().toString().isEmpty()) {
            nameET.setError("Required");
        } else if (studentIDET.getText().toString().isEmpty()) {
            studentIDET.setError("Required");
        } else if (emailIDET.getText().toString().isEmpty()) {
            emailIDET.setError("Required");
        } else if (passwordET.getText().toString().isEmpty()) {
            passwordET.setError("Required");
        } else if (passwordET.getText().toString().length() < 6) {
            passwordET.setError("Password must be at least 6 digit");
        } else if (contactET.getText().toString().isEmpty()) {
            contactET.setError("Required");
        }

        else {
            final String name = nameET.getText().toString().trim();
            final String studentID = studentIDET.getText().toString().trim();
            final String email = emailIDET.getText().toString().trim();
            final String password = passwordET.getText().toString().trim();
            final String contact = contactET.getText().toString().trim();

            progressBar.setVisibility(View.VISIBLE);

            fAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {

                                User user = new User(name, studentID, email, password, contact);

                                FirebaseDatabase.getInstance().getReference("Users")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        progressBar.setVisibility(View.GONE);
                                        if (task.isSuccessful()) {
                                            Toast.makeText(Registration.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                        } else {
                                            //display a failure message
                                        }
                                    }
                                });

                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(Registration.this, "Registration Successful", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(Registration.this, login.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(Registration.this, "Error!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.INVISIBLE);
                            }
                        }
                    });

        }
    }

    public void Already_have_account(View view) {
        Intent intent = new Intent(Registration.this, login.class);
        startActivity(intent);
        finish();
    }
}
