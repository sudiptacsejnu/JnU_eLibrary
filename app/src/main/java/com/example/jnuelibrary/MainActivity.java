package com.example.jnuelibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth fAuth;
    FirebaseDatabase fdatabase;
    TextView userNameTV, studentIDTV;
    DatabaseReference databaseReference;

    private long backPressedTime;
    private Toast backToast;
    private String uID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fAuth = FirebaseAuth.getInstance();
        fdatabase = FirebaseDatabase.getInstance();
        uID = fAuth.getUid();

        if(fAuth.getCurrentUser().getEmail().matches("admin@gmail.com")){

        }

        else {
            Intent intent = new Intent(MainActivity.this, SeeBookList.class);
            startActivity(intent);
            finish();
        }

        userNameTV = findViewById(R.id.userNameTV);
        studentIDTV = findViewById(R.id.studentIDTV);

        databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String userName = dataSnapshot.child(uID).child("name").getValue().toString();
                String studentID = dataSnapshot.child(uID).child("studentID").getValue().toString();

                userNameTV.setText(userName);
                studentIDTV.setText(studentID);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




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
