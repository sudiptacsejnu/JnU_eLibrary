package com.example.jnuelibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SeeBorrowList extends AppCompatActivity {

    private RecyclerView mRecyclerViewBorrow;
    private MyAdapterBorrow myAdapterBorrow;
    private List<BorrowInformation> borrowInformationList;
    DatabaseReference databaseReferenceBorrow;
    DatabaseReference databaseReference;
    DatabaseReference databaseReferenceUser;

    FirebaseAuth fAuth;

    private String userID;
    private String uID;
    private String uName;
    private int bookQuantityCount;
    private String bookQuantity;

    EditText searchSeeBorrowList;
    ProgressBar seeBorrowListPB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_borrow_list);

        mRecyclerViewBorrow = findViewById(R.id.recyclerview_Borrow);
        searchSeeBorrowList = findViewById(R.id.search_see_borrow_list);
        seeBorrowListPB = findViewById(R.id.seeBorrowListPB);

        mRecyclerViewBorrow.setHasFixedSize(true);
        mRecyclerViewBorrow.setLayoutManager(new LinearLayoutManager(this));

        fAuth = FirebaseAuth.getInstance();
        uID = FirebaseAuth.getInstance().getUid();

        databaseReferenceUser = FirebaseDatabase.getInstance().getReference("Users");

        databaseReferenceUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                uName = dataSnapshot.child(uID).child("name").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        userID = getIntent().getStringExtra("uID");

        borrowInformationList = new ArrayList<>();
        databaseReferenceBorrow = FirebaseDatabase.getInstance().getReference("BorrowInformation");

        databaseReferenceBorrow.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    BorrowInformation borrowInformation = dataSnapshot1.getValue(BorrowInformation.class);
                        if (borrowInformation.getStatus() == 0) {
                            if (fAuth.getCurrentUser().getEmail().matches("admin@gmail.com")) {
                                borrowInformationList.add(borrowInformation);
                            }
                            else {
                                if(borrowInformation.getUserName().matches(uName)){
                                    borrowInformationList.add(borrowInformation);
                                }
                            }
                        }


                    }


                seeBorrowListPB.setVisibility(View.GONE);

                myAdapterBorrow = new MyAdapterBorrow(SeeBorrowList.this, borrowInformationList);
                mRecyclerViewBorrow.setAdapter(myAdapterBorrow);

                myAdapterBorrow.setOnItemClickListener(new MyAdapterBorrow.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        if (fAuth.getCurrentUser().getEmail().matches("admin@gmail.com")) {

                        String borrowBookID = borrowInformationList.get(position).getBookID();
                        String borrowUserName = borrowInformationList.get(position).getUserName();

                        databaseReference = FirebaseDatabase.getInstance().getReference("Books");

                        databaseReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                bookQuantity = dataSnapshot.child(borrowBookID).child("bquantity").getValue().toString();

                                bookQuantityCount = Integer.parseInt(bookQuantity);

                                Intent intent = new Intent(SeeBorrowList.this, ReturnBook.class);
                                intent.putExtra("returnBookID", borrowBookID);
                                intent.putExtra("returnBookQuantity", bookQuantityCount);
                                intent.putExtra("returnBookUserName", borrowUserName);
                                startActivity(intent);
                                finish();

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(SeeBorrowList.this, "Error :" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
