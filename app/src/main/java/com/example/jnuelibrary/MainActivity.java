package com.example.jnuelibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth fAuth;
    FirebaseDatabase fdatabase;
    TextView userNameTV;

    private long backPressedTime;
    private Toast backToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fAuth = FirebaseAuth.getInstance();
        fdatabase = FirebaseDatabase.getInstance();

        if(fAuth.getCurrentUser().getEmail().matches("admin@gmail.com")){

        }

        else {
            Intent intent = new Intent(MainActivity.this, SeeBookList.class);
            startActivity(intent);
            finish();
        }

        userNameTV = findViewById(R.id.userNameTV);

        userNameTV.setText(fAuth.getCurrentUser().getEmail());


    }

    public void Logout(View view) {
        fAuth.signOut();
        Intent intent = new Intent(MainActivity.this, login.class);
        startActivity(intent);
        finish();

    }

    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            backToast.cancel();
            super.onBackPressed();
            return;
        } else {
            backToast = Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT);
            backToast.show();
        }

        backPressedTime = System.currentTimeMillis();
    }

    public void SeeBookList(View view) {
        Intent intent = new Intent(MainActivity.this, SeeBookList.class);
        startActivity(intent);
    }

    public void AddNewBook(View view) {
        Intent intent = new Intent(MainActivity.this, AddNewBook.class);
        startActivity(intent);
    }
}
