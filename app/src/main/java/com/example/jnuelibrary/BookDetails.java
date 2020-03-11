package com.example.jnuelibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BookDetails extends AppCompatActivity {

    TextView bookNameDetailsTV, bookWritterDetailsTV, bookCategoryDetailsTV, bookDescriptionDetailsTV, bookQuantityDetailsTV;
    DatabaseReference databaseReference;
    private String bookID;
    private int bookQuantityCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);

        bookID = getIntent().getStringExtra("bookID");

        bookNameDetailsTV = findViewById(R.id.bookNameDetailsET);
        bookWritterDetailsTV = findViewById(R.id.bookWritterDetailsET);
        bookCategoryDetailsTV = findViewById(R.id.bookCategoryDetailsET);
        bookDescriptionDetailsTV = findViewById(R.id.bookDescriptionDetailsET);
        bookQuantityDetailsTV = findViewById(R.id.bookQuantityDetailsET);


        databaseReference = FirebaseDatabase.getInstance().getReference("Books");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String bookName = dataSnapshot.child(bookID).child("bname").getValue().toString();
                String bookWritter = dataSnapshot.child(bookID).child("bwritter").getValue().toString();
                String bookCategory = dataSnapshot.child(bookID).child("bcategory").getValue().toString();
                String bookDescription = dataSnapshot.child(bookID).child("bdescription").getValue().toString();
                String bookQuantity = dataSnapshot.child(bookID).child("bquantity").getValue().toString();

                bookQuantityCount = Integer.parseInt(bookQuantity);

                bookNameDetailsTV.setText(bookName);
                bookWritterDetailsTV.setText(bookWritter);
                bookCategoryDetailsTV.setText(bookCategory);
                bookDescriptionDetailsTV.setText(bookDescription);
                bookQuantityDetailsTV.setText(bookQuantity);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void BookBorrow(View view) {

        if(bookQuantityCount>0)
        {
            Intent intent = new Intent(BookDetails.this, BorrowBook.class);
            intent.putExtra("borrowBookID",bookID);
            intent.putExtra("borrowBookQuantity",bookQuantityCount);
            startActivity(intent);
        }

        else
        {
            Toast.makeText(this, "Stock Finished.", Toast.LENGTH_SHORT).show();
        }


    }
}
