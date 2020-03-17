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

        borrowInformationList = new ArrayList<>();
        databaseReferenceBorrow = FirebaseDatabase.getInstance().getReference("BorrowInformation");

        databaseReferenceBorrow.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    BorrowInformation borrowInformation = dataSnapshot1.getValue(BorrowInformation.class);
                    borrowInformationList.add(borrowInformation);
                }

                seeBorrowListPB.setVisibility(View.GONE);

                myAdapterBorrow = new MyAdapterBorrow(SeeBorrowList.this, borrowInformationList);
                mRecyclerViewBorrow.setAdapter(myAdapterBorrow);

                myAdapterBorrow.setOnItemClickListener(new MyAdapterBorrow.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        String borrowBookID = borrowInformationList.get(position).getBookID();
                        String borrowUserName = borrowInformationList.get(position).getUserName();

                        Toast.makeText(SeeBorrowList.this, borrowBookID + " is selected", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(SeeBorrowList.this, SeeBorrowList.class);
                        intent.putExtra("borrowBookID", borrowBookID);
                        startActivity(intent);
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
