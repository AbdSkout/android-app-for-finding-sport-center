package com.example.b7sport;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GroupProfile extends AppCompatActivity {
    public TextView textid, textName, textType, textStreet,textNeighborh,textActivity,textLighting,textSportType,groupname ,numberofplayers,isprivate;//I dont know if I must add the lat and lon
    Button mJoinGroup,mCancelJpinGroup;
    String userID1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_profile);

        textName =findViewById(R.id.gr_name3);
        textType = findViewById(R.id.gr_type3);
        textStreet = findViewById(R.id.gr_street3);
        textNeighborh =findViewById(R.id.gr_gr_neighbor3);
        textActivity = findViewById(R.id.gr_activity3);
        textSportType =findViewById(R.id.gr_sporttype3);
        textLighting = findViewById(R.id.gr_lighting3);
        textid = findViewById(R.id.gr_id3);
        groupname = findViewById(R.id.sg_grname3);
        numberofplayers = findViewById(R.id.sg_playersnumber3);
        isprivate = findViewById(R.id.sg_isprivate3);
        mJoinGroup = findViewById(R.id.JoinpGroup);

        textid.setText(String.valueOf(GroupAdapter.selected_group.getArenaid()));
        textName.setText("שם מגרש : " + GroupAdapter.selected_group.getArenaname());
        textType.setText("סוג מגרש : " +String.valueOf(GroupAdapter.selected_group.getArenatype()));
        textStreet.setText("כביש : " +String.valueOf(GroupAdapter.selected_group.getArenastreet()));
        textNeighborh.setText("שכונה : " +GroupAdapter.selected_group.getArenaneighbor());
        textActivity.setText("פעילות : " +GroupAdapter.selected_group.getArenaactivity());
        textLighting.setText("תאורה : " +GroupAdapter.selected_group.getArenalighing());
        textSportType.setText("סוג ספורט : " +GroupAdapter.selected_group.getArenasport_type());
        groupname.setText("שם קבוצה: " + GroupAdapter.selected_group.getGroupname());
        numberofplayers.setText("מספר שחקנים בקבוצה: " + GroupAdapter.selected_group.getPlayersnumber());

        Bundle bundle = getIntent().getExtras();
        userID1 = bundle.getString("emailadd");

        final Intent myIntent = new Intent(GroupProfile.this, MainActivity.class);
        myIntent.putExtra("emailadd", userID1);


        if(GroupAdapter.selected_group.isIsprivate())
            isprivate.setText("קבוצה פרטית");
        else
            isprivate.setText("קבוצה ציבורית");


        mCancelJpinGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(myIntent);
            }
        });

        mJoinGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }
}