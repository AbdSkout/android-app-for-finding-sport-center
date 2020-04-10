package com.example.b7sport;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity {
    private TextView mName,mEmail,mPhonenumber;
    private FirebaseDatabase database;
    private DatabaseReference UserRef;
    private static final String USERS = "EDMT_FIREBASE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Bundle bundle = getIntent().getExtras();
        final String userID1 = bundle.getString("email");

        final Intent myIntent = new Intent(Profile.this,MainActivity.class);
        myIntent.putExtra("email",userID1);

        mName = findViewById(R.id.FullName1);
        mEmail = findViewById(R.id.Email1);
        mPhonenumber = findViewById(R.id.PhoneNumber1);

        database = FirebaseDatabase.getInstance();
        UserRef = database.getReference(USERS);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.profile);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        startActivity(myIntent);
                        overridePendingTransition(0,0);
                        return true;
//                    case R.id.games:
//                        startActivity(new Intent(getApplicationContext(),About.class));
//                        overridePendingTransition(0,0);
//                        return true;
                    case R.id.profile:
                        UserRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for(DataSnapshot ds : dataSnapshot.getChildren()){
                                    if(ds.child("email").getValue().equals(userID1)){
                                        mName.setText(ds.child("name").getValue(String.class));
                                        mEmail.setText(ds.child("email").getValue(String.class));
                                        mPhonenumber.setText(ds.child("phoneNumber").getValue(String.class));
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        return true;
                }
                return false;
            }
        });


    }
}
