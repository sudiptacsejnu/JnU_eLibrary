package com.example.jnuelibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class SeeBookList extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private MyAdapter myAdapter;
    private List<BookInformation> bookInformationList;
    DatabaseReference databaseReference;

    EditText searchSeeBookList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_book_list);

        mRecyclerView = findViewById(R.id.recyclerview_Book);
        searchSeeBookList = findViewById(R.id.search_see_book_list);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        bookInformationList = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("Books");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                {
                    BookInformation bookInformation = dataSnapshot1.getValue(BookInformation.class);
                    bookInformationList.add(bookInformation);
                }

                myAdapter = new MyAdapter(SeeBookList.this, bookInformationList);
                mRecyclerView.setAdapter(myAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(SeeBookList.this, "Error :"+ databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        searchSeeBookList.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!s.toString().isEmpty())
                {
                    search(s.toString());
                }
                else
                {
                    search("");
                }
            }
        });



        /*mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_Book);
        new FirebaseDatabaseHelper().readBooks(new FirebaseDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<BookInformation> books, List<String> keys) {
                new RecyclerView_Config().setConfig(mRecyclerView, SeeBookList.this,
                        books, keys);
            }

            @Override
            public void DataIsInserted() {

            }

            @Override
            public void DataIsUpdated() {

            }

            @Override
            public void DataIsDeleted() {

            }
        });*/


    }

    private void search(String toString)
    {
        //for bname
        Query querybname = databaseReference.orderByChild("bname")
                .startAt(toString)
                .endAt(toString + "\uf8ff");

        querybname.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChildren())
                {
                    bookInformationList.clear();
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                    {
                        final BookInformation bookInformation = dataSnapshot1.getValue(BookInformation.class);
                        bookInformationList.add(bookInformation);
                    }

                    myAdapter = new MyAdapter(SeeBookList.this, bookInformationList);
                    mRecyclerView.setAdapter(myAdapter);
                    myAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //for bwritter
        Query querybwritter = databaseReference.orderByChild("bwritter")
                .startAt(toString)
                .endAt(toString + "\uf8ff");

        querybwritter.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChildren())
                {
                    bookInformationList.clear();
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                    {
                        final BookInformation bookInformation = dataSnapshot1.getValue(BookInformation.class);
                        bookInformationList.add(bookInformation);
                    }

                    myAdapter = new MyAdapter(SeeBookList.this, bookInformationList);
                    mRecyclerView.setAdapter(myAdapter);
                    myAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //for bcatagory
        Query querybcatagory = databaseReference.orderByChild("bcatagory")
                .startAt(toString)
                .endAt(toString + "\uf8ff");

        querybcatagory.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChildren())
                {
                    bookInformationList.clear();
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                    {
                        final BookInformation bookInformation = dataSnapshot1.getValue(BookInformation.class);
                        bookInformationList.add(bookInformation);
                    }

                    myAdapter = new MyAdapter(SeeBookList.this, bookInformationList);
                    mRecyclerView.setAdapter(myAdapter);
                    myAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
