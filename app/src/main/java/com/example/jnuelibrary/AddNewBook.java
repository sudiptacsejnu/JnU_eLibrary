package com.example.jnuelibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddNewBook extends AppCompatActivity {

    EditText bookNameET, bookIDET, bookWriterET,bookDescriptionET, bookCatagoryET;
    ProgressBar progressBarANB;
    DatabaseReference databaseReference;
    String uId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_book);

        uId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        bookIDET = findViewById(R.id.bookIDET);
        bookNameET = findViewById(R.id.bookNameET);
        bookWriterET = findViewById(R.id.bookWriterET);
        bookCatagoryET = findViewById(R.id.bookCatagoryET);
        bookDescriptionET = findViewById(R.id.bookDescriptionET);

        progressBarANB = findViewById(R.id.ProgressbarANB);

        databaseReference = FirebaseDatabase.getInstance().getReference("Books");
    }

    public void Submit(View view) {
        progressBarANB.setVisibility(View.VISIBLE);


        if(bookIDET.getText().toString().trim().isEmpty()){
            bookIDET.setError("Required");
        }
        else if(bookNameET.getText().toString().trim().isEmpty()){
            bookNameET.setError("Required");
        }
        else if(bookWriterET.getText().toString().trim().isEmpty()){
            bookWriterET.setError("Required");
        }
        else if(bookDescriptionET.getText().toString().trim().isEmpty()){
            bookDescriptionET.setError("Required");
        }
        else if(bookCatagoryET.getText().toString().trim().isEmpty()){
            bookCatagoryET.setError("Required");
        }
        else {
            final String bookID = bookIDET.getText().toString().trim();
            final String bookName = bookNameET.getText().toString().trim();
            final String bookWritter = bookWriterET.getText().toString().trim();
            final String bookDescription = bookDescriptionET.getText().toString().trim();
            final String bookCatagory = bookCatagoryET.getText().toString().trim();


            BookInformation bookInformation = new BookInformation(bookID,bookName, bookWritter, bookDescription, bookCatagory);

            //Log.d("SUDIPTA", bookInformation.toString());
            //databaseReference.child(uId+"/"+bookID).setValue(bookInformation);

        databaseReference.child(bookID)
                .setValue(bookInformation).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                progressBarANB.setVisibility(View.INVISIBLE);
                if (task.isSuccessful()) {
                    Toast.makeText(AddNewBook.this, "Book Information saved Successfully", Toast.LENGTH_SHORT).show();
                     bookIDET.setText("");
                     bookNameET.setText("");
                     bookWriterET.setText("");
                     bookDescriptionET.setText("");
                     bookCatagoryET.setText("");
                }
                else {
                    //display a failure message
                    Toast.makeText(AddNewBook.this, "Error!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    progressBarANB.setVisibility(View.GONE);

                }
            }
        });



    }

    }
}
