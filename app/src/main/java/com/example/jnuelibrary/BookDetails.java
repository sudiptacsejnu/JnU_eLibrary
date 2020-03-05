package com.example.jnuelibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BookDetails extends AppCompatActivity {

    TextView bookNameDetailsTV, bookWritterDetailsTV, bookCategoryDetailsTV, bookDescriptionDetailsTV;
    DatabaseReference databaseReference;
    private String bookID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);

        bookID = getIntent().getStringExtra("bookID");

        bookNameDetailsTV = findViewById(R.id.bookNameDetailsET);
        bookWritterDetailsTV = findViewById(R.id.bookWritterDetailsET);
        bookCategoryDetailsTV = findViewById(R.id.bookCategoryDetailsET);
        bookDescriptionDetailsTV = findViewById(R.id.bookDescriptionDetailsET);


        databaseReference = FirebaseDatabase.getInstance().getReference("Books");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String bookName = dataSnapshot.child(bookID).child("bname").getValue().toString();
                String bookWritter = dataSnapshot.child(bookID).child("bwritter").getValue().toString();
                String bookCategory = dataSnapshot.child(bookID).child("bcatagory").getValue().toString();
                String bookDescription = dataSnapshot.child(bookID).child("bdescription").getValue().toString();

                bookNameDetailsTV.setText(bookName);
                bookWritterDetailsTV.setText(bookWritter);
                bookCategoryDetailsTV.setText(bookCategory);
                bookDescriptionDetailsTV.setText(bookDescription);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
