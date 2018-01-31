package com.jk.foodbla;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Firebase extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase);

        // Open connection to DB
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference dbRef = db.getReference("count");

        // Write to DB
        dbRef.setValue("1");

        // Read from DB
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Method called once with initial value and
                // whenever value is updated
                String val = dataSnapshot.getValue(String.class);
                Log.d("READ", "Read value: " + val);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Failed to read value
                Log.w("CANCEL", "Failed to read value", databaseError.toException());
            }
        });

        dbRef.setValue("2");

        // Test user
        User testUser = createTestUser();
        addUserToDB(db, testUser);

        testUser.email = "updatedEmail";
        testUser.address = "changedAddress";

        // Update User
        DatabaseReference updatedRef = db.getReference().child("users").child(testUser.userId);
        updatedRef.setValue(testUser);

    }

    private void addUserToDB(FirebaseDatabase db, User user){
        DatabaseReference ref = db.getReference();
        ref.child("users").child(user.userId).setValue(user);

        DatabaseReference ref2 = db.getReference().child("users").child(user.userId);

        ref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // User data changed
                User changedUser = dataSnapshot.getValue(User.class);
                Log.d("USER", String.format("User changed: %s, %s, %s, %s", new String[]{
                        changedUser.userId, changedUser.email, changedUser.address, changedUser.username
                }));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("WARNING", "could not read from database");
            }
        });
    }

    private User createTestUser(){
        User user = new User("username", "userEmail", "userAddress", "12345");
        return user;
    }

    public void openOrderActivity(){
        // Create Intent to open new activity
        Intent activityIntent = new Intent(this, OrderNoTabActivity.class);

        //TODO: Add messages to intent

        startActivity(activityIntent);
        
    }

    public void orderButtonPressed(View v){
        openOrderActivity();
    }

    public void settingsButtonPressed(View v){

    }

    public void infoButtonPressed(View v){

    }
}
