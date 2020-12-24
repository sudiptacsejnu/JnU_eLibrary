package com.example.jnuelibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ReturnBook extends AppCompatActivity {

    TextView returnUserNameTV, returnBookNameTV;
    private String returnBookID;
    private String returnUserName;
    private int bookQuantity;
    private int returnStatus = 1;
    private String UserName;

    private String uID;
    private long maxid = 0;
    DatabaseReference databaseReferenceBook;
    DatabaseReference databaseReferenceUser;
    DatabaseReference databaseReferenceReturn;
    DatabaseReference databaseReferenceBorrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return_book);

        returnUserNameTV = findViewById(R.id.returnUserNameTV);
        returnBookNameTV = findViewById(R.id.returnBookNameTV);

        returnBookID = getIntent().getStringExtra("returnBookID");
        bookQuantity = getIntent().getIntExtra("returnBookQuantity",0);
        UserName = getIntent().getStringExtra("returnBookUserName");
        //bookQuantityInt = Integer.parseInt(bookQuantity);
        //Toast.makeText(this, bookQuantity, Toast.LENGTH_SHORT).show();

        uID = FirebaseAuth.getInstance().getUid();

        databaseReferenceBook = FirebaseDatabase.getInstance().getReference("Books");
        databaseReferenceUser = FirebaseDatabase.getInstance().getReference("Users");
        databaseReferenceReturn = FirebaseDatabase.getInstance().getReference("ReturnInformation");

        databaseReferenceReturn.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    maxid = (dataSnapshot.getChildrenCount());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        databaseReferenceUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //returnUserName = dataSnapshot.child(uID).child("name").getValue().toString();
                returnUserName = UserName;

                returnUserNameTV.setText(returnUserName);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        databaseReferenceBook.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String returnBookName = dataSnapshot.child(returnBookID).child("bname").getValue().toString();

                returnBookNameTV.setText(returnBookName);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void BookReturnConform(View view) {

        int returnBookQuantityUpdate =   bookQuantity + 1 ;

        databaseReferenceBook.child(returnBookID)
                .child("bquantity").setValue(Integer.toString(returnBookQuantityUpdate)).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {

                    ReturnInformation returnInformation = new ReturnInformation(returnUserName, returnBookID);

                    databaseReferenceReturn.child(String.valueOf(maxid))
                            .setValue(returnInformation).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(ReturnBook.this, "Book Information saved Successfully", Toast.LENGTH_SHORT).show();

                                databaseReferenceBorrow = FirebaseDatabase.getInstance().getReference("BorrowInformation");

                                databaseReferenceBorrow.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                            BorrowInformation borrowInformation = dataSnapshot1.getValue(BorrowInformation.class);

                                            if (borrowInformation.getBookID().matches(returnBookID)) {
                                                if(borrowInformation.getUserName().matches(returnUserName)) {
                                                    databaseReferenceBorrow.child(dataSnapshot1.getRef().getKey().toString())
                                                            .child("status").setValue(returnStatus);

                                                }
                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });

                                Intent intent = new Intent(ReturnBook.this, MainActivity.class);
                                startActivity(intent);
                                finish();

                            }
                            else {

                                Toast.makeText(ReturnBook.this, "Error!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();


                            }
                        }
                    });

                }
                else {
                    //display a failure message
                    Toast.makeText(ReturnBook.this, "Error!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}