package com.example.b7sport;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.awt.font.TextAttribute;
import java.io.FileNotFoundException;

public class blockuser extends AppCompatActivity {

    EditText Name;
    Button but_block;
    Button btn_delete;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blockuser);

           Name=findViewById(R.id.nblock);
           but_block=findViewById(R.id.Bblock);
           btn_delete=findViewById(R.id.deltebtn);
           final DatabaseReference ref = database.getReference("EDMT_FIREBASE");
          final DatabaseReference ref1 = database.getReference("EDMT_FIREBASE");

        but_block.setOnClickListener(new View.OnClickListener(){
               @Override
               public void onClick(View v) {
                   ref.addValueEventListener(new ValueEventListener() {
                         @Override
                         public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                String Name1=Name.getText().toString().trim();
                                String name1="";
                                String name2;
                                int flag=0;
                             for (DataSnapshot d : dataSnapshot.getChildren())
                             {
                                    name2=d.child("email").getValue().toString();

                                    if(name2.equals(Name1) )
                                    {
                                        flag=1;
                                        name1=d.getKey().toString();

                                    }

                             }
                           if(flag==1) {

                              ref1.child(name1).child("flag").setValue("1");
                               Log.d("", "user is here");


                           }

                               else
                               Log.d("","falied");
                         }


                         @Override
                         public void onCancelled(@NonNull DatabaseError databaseError) {

                         }


                     });





               }


           });




           btn_delete.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {

                   ref.addValueEventListener(new ValueEventListener() {
                       @Override
                       public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                           String Name1=Name.getText().toString().trim();
                           String name1="";
                           String name2;
                           int flag=0;
                           for (DataSnapshot d : dataSnapshot.getChildren())
                           {
                               name2=d.child("email").getValue().toString();

                               if(name2.equals(Name1) )
                               {
                                   flag=1;
                                   name1=d.getKey().toString();

                               }

                           }
                           if(flag==1) {

                               ref1.child(name1).removeValue();
                               Log.d("", "user is here");


                           }

                           else
                               Log.d("","falied");
                       }


                       @Override
                       public void onCancelled(@NonNull DatabaseError databaseError) {

                       }


                   });






               }
           });


    }

}
