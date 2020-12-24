package com.example.jnuelibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;


public class UserActivity extends AppCompatActivity {

    FirebaseAuth fAuth;
    FirebaseDatabase fdatabase;
    private long backPressedTime;
    private Toast backToast;

    private String uID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

    }

    public void UserSeeBookList(View view) {

        Intent intent = new Intent(UserActivity.this, SeeBookList.class);
        startActivity(intent);
    }

    public void UserSeeBorrowList(View view) {

        fAuth = FirebaseAuth.getInstance();
        fdatabase = FirebaseDatabase.getInstance();
        uID = fAuth.getUid();

        if(fAuth.getCurrentUser().getEmail().matches("admin@gmail.com")){

        }

        else {
            Intent intent = new Intent(UserActivity.this, SeeBorrowList.class);
            intent.putExtra("uID",uID);
            startActivity(intent);
        }

    }

    public void UserLogout(View view) {
        fAuth.signOut();
        Intent intent = new Intent(UserActivity.this, login.class);
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
}
