package com.example.jnuelibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class SeeBookList extends AppCompatActivity {

    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_book_list);

        fAuth = FirebaseAuth.getInstance();


    }

    public void Logout(View view) {
        fAuth.signOut();
        Intent intent = new Intent(SeeBookList.this, login.class);
        startActivity(intent);
        finish();
    }
}
