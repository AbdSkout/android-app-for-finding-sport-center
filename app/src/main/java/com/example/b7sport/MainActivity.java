package com.example.b7sport;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;



import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;



import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.InterruptedIOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView mName,mEmail,mPhonenumber;
    TextView mComplaint;

    Button mLogOutButton,mCreateGroupBtn,mShowGroupsbtn;
    static public String userID1;
    FirebaseAuth fAuth;
    ProgressDialog dialog;
    FirebaseDatabase database;
    DatabaseReference reference,ref;
    //DatabaseReference reference;
  //  DatabaseReference ref;


    //Recycler View
    RecyclerView mRecyclerView;
    List<InfoFromDataBase> usersinfo = new ArrayList<>();
    RecyclerView.LayoutManager layoutManager;
    FirebaseFirestore fStore;
    UsersAdapter adapeter;
    ProgressDialog pd;
    TextView msg;
    static String emailID;

    TextView mChange;

//    Button mChange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        msg=findViewById(R.id.msg_admin);
        pd = new ProgressDialog(this);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("0");
        mLogOutButton = findViewById(R.id.LogOutBtn);
        fAuth = FirebaseAuth.getInstance();
        mName = findViewById(R.id.textView5);
        mComplaint = findViewById(R.id.textView10);
        mCreateGroupBtn=findViewById(R.id.Creaegroupbtn);
        mShowGroupsbtn=findViewById(R.id.showGroups);
        //Init Database
        fStore = FirebaseFirestore.getInstance();




        //Intialazing
        //mRecyclerView= findViewById(R.id.recyclerView);
        //mRecyclerView.setHasFixedSize(true);
        //layoutManager = new LinearLayoutManager(this);
        //mRecyclerView.setLayoutManager(layoutManager);
        //showdata();


                          mChange = findViewById(R.id.chanpassmainv);
                          mChange.setOnClickListener(new View.OnClickListener() {                                 
                              @Override                                                                           
                              public void onClick(View v) {                                                       
                                  startActivity(new Intent(getApplicationContext(), ChangePassword.class));       
                              }
                          });                                                                                     

        mChange = findViewById(R.id.chanpassmainv);
        mChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ChangePassword.class));
            }
        });

        mCreateGroupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  Intent intentGroup = new Intent(getApplicationContext(),CreatePublicGroupActivity.class);
             //   intentGroup.putExtra("emailadd",userID1);
                startActivity(new Intent(getApplicationContext(),RecyclerViewArena.class));
             //   startActivity(intentGroup);
            }
        });

        Bundle bundle = getIntent().getExtras();
        String userID1 = Login.Email.toString();
      //  userID1 = bundle.getString("emailadd");
        //emailID = userID1;
        final Intent intent1 = new Intent(MainActivity.this,Complaint.class);
        intent1.putExtra("emailadd",userID1);
        //final Intent intentJoinGroup = new Intent(MainActivity.this,RecyclerViewGroup.class);

      //  final Intent intent1 = new Intent(MainActivity.this,Complaint.class);
      //  intent1.putExtra("emailadd",userID1);
        final Intent intentJoinGroup = new Intent(MainActivity.this,GroupProfile.class);
        intentJoinGroup.putExtra("emailadd",userID1);

        mComplaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent1);
            }
        });

          mChange = findViewById(R.id.chanpassmainv);
          mChange.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  startActivity(new Intent(getApplicationContext(), ChangePassword.class));
              }
          });


            mCreateGroupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(getApplicationContext(),CreatePublicGroupActivity.class);
                startActivity(new Intent(getApplicationContext(),RecyclerViewArena.class));
               // startActivity(intent);
            }
        });



        dialog = new ProgressDialog(this);
        mLogOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();

                FirebaseAuth.getInstance().signOut();
                dialog.dismiss();
                startActivity(new Intent(getApplicationContext(), Login.class));
                finish();
            }});

        mShowGroupsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //   Intent intent= new Intent(getApplicationContext(),RecyclerViewGroup.class);
                startActivity(intentJoinGroup);
                finish();
            }});
        mShowGroupsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(),RecyclerViewGroup.class);
                startActivity(intent);
            }
        });


        final Intent myIntent = new Intent(MainActivity.this, Profile.class);
        myIntent.putExtra("emailadd", userID1);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            //TODO : Fix Profile View
            //The navbar is not sending correct info to the actions
            //Try to add threading to make the process faster
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.profile:
                        startActivity(myIntent);
                        overridePendingTransition(0, 0);
                        return true;
//                    case R.id.about:
//                        startActivity(new Intent(getApplicationContext(),About.class));
//                        overridePendingTransition(0,0);
//                        return true;
                    case R.id.home:
                        return true;
                }
                return false;
            }
        });


       get_msg();

    }



  /*  private void showdata() {







    private void showdata() {
>>>>>>> master
        pd.setTitle("טוען נתונים...");
        pd.show();
        pd.setCancelable(false);
        fStore.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        pd.dismiss();
                        for (DocumentSnapshot doc : task.getResult()) {
                            InfoFromDataBase info = new InfoFromDataBase(doc.getString("Email"),
                                    doc.getString("PhoneNumber"),
                                    doc.getString("FullName"));
                            usersinfo.add(info);
                        }
                        adapeter = new UsersAdapter(MainActivity.this, usersinfo);
                        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        mRecyclerView.setAdapter(adapeter);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(MainActivity.this, "Error Loading the Info!", Toast.LENGTH_SHORT).show();
                    }
                });
    }*/
  private  void get_msg()
  {
      ref=database.getReference("Message");
      ref.addValueEventListener(new ValueEventListener() {
          @Override
          public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
              msg.setText(dataSnapshot.getValue().toString());
          }

          @Override
          public void onCancelled(@NonNull DatabaseError databaseError) {

          }
      });


  }
/*
    }
    private  void get_msg()
    {
         ref=database.getReference("Message");
         ref.addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                 msg.setText(dataSnapshot.getValue().toString());
             }

             @Override
             public void onCancelled(@NonNull DatabaseError databaseError) {

             }
         });


    }
*/
}