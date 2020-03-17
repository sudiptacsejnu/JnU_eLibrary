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

public class SeeUserList extends AppCompatActivity {

    private RecyclerView mRecyclerViewUser;
    private MyAdapterUser myAdapterUser;
    private List<User> userList;
    DatabaseReference databaseReferenceUser;

    EditText searchSeeUserList;
    ProgressBar seeUserListPB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_user_list);

        mRecyclerViewUser = findViewById(R.id.recyclerview_User);
        searchSeeUserList = findViewById(R.id.search_see_user_list);
        seeUserListPB = findViewById(R.id.seeUserListPB);

        mRecyclerViewUser.setHasFixedSize(true);
        mRecyclerViewUser.setLayoutManager(new LinearLayoutManager(this));

        userList = new ArrayList<>();
        databaseReferenceUser = FirebaseDatabase.getInstance().getReference("Users");

        databaseReferenceUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    User user = dataSnapshot1.getValue(User.class);
                    userList.add(user);
                }

                seeUserListPB.setVisibility(View.GONE);

                myAdapterUser = new MyAdapterUser(SeeUserList.this, userList);
                mRecyclerViewUser.setAdapter(myAdapterUser);

                myAdapterUser.setOnItemClickListener(new MyAdapterUser.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        String userName = userList.get(position).getName();
                        String userID = userList.get(position).getStudentID();

                        Toast.makeText(SeeUserList.this, userName + " is selected", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(SeeUserList.this, SeeUserList.class);
                        intent.putExtra("userName", userName);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(SeeUserList.this, "Error :" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
