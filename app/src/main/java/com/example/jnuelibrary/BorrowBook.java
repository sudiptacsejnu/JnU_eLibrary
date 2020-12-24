package com.example.jnuelibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
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

public class BorrowBook extends AppCompatActivity {

    TextView borrowUserNameTV, borrowBookNameTV;
    private String borrowBookID;
    private String borrowBookName;
    private int borrowBookQuantity;
    private int initStatus = 0;
    private String uID;
    private long maxid = 0;
    DatabaseReference databaseReferenceBook;
    DatabaseReference databaseReferenceUser;
    DatabaseReference databaseReferenceBorrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrow_book);

        createNotificationChannel();

        borrowUserNameTV = findViewById(R.id.borrowUserNameTV);
        borrowBookNameTV = findViewById(R.id.borrowBookNameTV);

        borrowBookID = getIntent().getStringExtra("borrowBookID");
        //borrowBookQuantity = Integer.parseInt(getIntent().getStringExtra("borrowBookQuantity"));
        borrowBookQuantity = getIntent().getIntExtra("borrowBookQuantity",0);
        Toast.makeText(this, borrowBookID, Toast.LENGTH_SHORT).show();

        uID = FirebaseAuth.getInstance().getUid();

        databaseReferenceBook = FirebaseDatabase.getInstance().getReference("Books");
        databaseReferenceUser = FirebaseDatabase.getInstance().getReference("Users");
        databaseReferenceBorrow = FirebaseDatabase.getInstance().getReference("BorrowInformation");

        databaseReferenceBorrow.addValueEventListener(new ValueEventListener() {
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
                borrowBookName = dataSnapshot.child(uID).child("name").getValue().toString();

                borrowUserNameTV.setText(borrowBookName);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        databaseReferenceBook.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String borrowBookName = dataSnapshot.child(borrowBookID).child("bname").getValue().toString();

                borrowBookNameTV.setText(borrowBookName);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void BookBorrowConform(View view) {

        int borrowBookQuantityUpdate = borrowBookQuantity-1;

        databaseReferenceBook.child(borrowBookID)
                .child("bquantity").setValue(Integer.toString(borrowBookQuantityUpdate)).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {

                    BorrowInformation borrowInformation = new BorrowInformation(borrowBookName, borrowBookID,initStatus);

                    databaseReferenceBorrow.child(String.valueOf(maxid))
                            .setValue(borrowInformation).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(BorrowBook.this, "Book Information saved Successfully", Toast.LENGTH_SHORT).show();

                                //alarm notification
                                Intent alarmIntent = new Intent(BorrowBook.this, ReminderBroadcast.class);
                                PendingIntent pendingIntent = PendingIntent.getBroadcast(BorrowBook.this, 0, alarmIntent, 0);

                                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

                                long timeAtButtonClick = System.currentTimeMillis();

                                long tenSecondsInMillis = 1000 * 10;

                                alarmManager.set(AlarmManager.RTC_WAKEUP,
                                        timeAtButtonClick + tenSecondsInMillis,
                                        pendingIntent);

                                Intent intent = new Intent(BorrowBook.this, BookDetails.class);
                                intent.putExtra("bookID",borrowBookID);
                                startActivity(intent);

                            }
                            else {
                                //display a failure message
                                Toast.makeText(BorrowBook.this, "Error!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();


                            }
                        }
                    });

                }
                else {
                    //display a failure message
                    Toast.makeText(BorrowBook.this, "Error!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "ReminderChannel";
            String description = "Channel for Reminder";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("notify", name, importance );
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
