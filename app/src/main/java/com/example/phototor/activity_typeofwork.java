package com.example.phototor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class activity_typeofwork extends AppCompatActivity implements View.OnClickListener {

    CheckBox family;
    CheckBox video;
    CheckBox objects;
    CheckBox events;
    CheckBox magnets;
    CheckBox studio;
    CheckBox copter;
    Button save;

    DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_typeofwork);

        family = (CheckBox) findViewById(R.id.familyChkBox);
        video = (CheckBox) findViewById(R.id.videoChkBox);
        objects = (CheckBox) findViewById(R.id.objectsChkBox);
        events = (CheckBox) findViewById(R.id.eventsChkBox);
        magnets = (CheckBox) findViewById(R.id.magnetsChkBox);
        studio = (CheckBox) findViewById(R.id.studioChkBox);
        save = (Button) findViewById(R.id.saveBtn);
        copter = (CheckBox) findViewById(R.id.copterChkBox);

        mAuth = FirebaseAuth.getInstance();


        // get user areas from database and turn the checkBox on:
        getUserAreas(new MyCallback() {
            @Override
            public void onCallback(Object value) {

                Map<String, Object> dataMap = (HashMap) value;

                for (Map.Entry<String, Object> entry : dataMap.entrySet()) {

                    String key = entry.getKey();
                    String val = entry.getValue().toString();

                    if (val.equals("yes")) {
                        checkBoxOn(key);
                    }

                }

            }
        });

    }

    @Override
    public void onClick(View view) {
        // onClick for save button

        if (view == save) {
            addOrRemoveType(family, "צילומי זוגיות ומשפחה");
            addOrRemoveType(video, "צילומי וידאו");
            addOrRemoveType(objects, "צילום אובייקטים");
            addOrRemoveType(events, "צילום אירועים");
            addOrRemoveType(magnets, "מגנטים לאירועים");
            addOrRemoveType(studio, "צילומי סטודיו");
            addOrRemoveType(copter, "צילומי רחפן");
            updateProfile();

            Toast.makeText(getApplicationContext(), "נשמר בהצלחה!",
                    Toast.LENGTH_SHORT).show();

        }

    }


    public void addOrRemoveType(CheckBox typeBtn, String typeName) {
        //If the user choose area: add the userID to area child in areas database.
        //Else: remove the userID from area child in areas database.

        String userID = mAuth.getUid();

        if (typeBtn.isChecked()) {
            final Map<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put(userID, "yes");
            dbRef.child("workTypes").child(typeName).updateChildren(dataMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {

                }
            });
        } else if (typeBtn.isChecked() == false) {
            dbRef.child("workTypes").child(typeName).child(userID).removeValue();
        }


    }

    public void updateProfile() {
        //update the userID child to "users" database.

        Map<String, Object> dataMap = new HashMap<String, Object>();
        String userID = mAuth.getUid();

        dataMap = updateAreasMap(dataMap, family, "צילומי זוגיות ומשפחה");
        dataMap = updateAreasMap(dataMap, video, "צילומי וידאו");
        dataMap = updateAreasMap(dataMap, objects, "צילום אובייקטים");
        dataMap = updateAreasMap(dataMap, events, "צילום אירועים");
        dataMap = updateAreasMap(dataMap, magnets, "מגנטים לאירועים");
        dataMap = updateAreasMap(dataMap, studio, "צילומי סטודיו");
        dataMap = updateAreasMap(dataMap, copter, "צילומי רחפן");


        dbRef.child("users").child(userID).child("myWorkTypes").updateChildren(dataMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        });


    }

    public Map updateAreasMap(Map map, CheckBox areaBox, String areaName) {

        if (areaBox.isChecked()) {
            map.put(areaName, "yes");

        } else {
            map.put(areaName, "no");
        }

        return map;
    }


    public void getUserAreas(final MyCallback call) {
        //Read user areas from database.

        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String ans = "";

                ArrayList<HashMap<String, Object>> list = new ArrayList<>();
                if (dataSnapshot == null) {
                    call.onCallback("null");
                }

                Object fieldsObj = new Object();
                HashMap fldObj;

                for (DataSnapshot shot : dataSnapshot.getChildren()) {

                    shot = shot.child(mAuth.getUid());

                    try {

                        fldObj = (HashMap) shot.getValue(fieldsObj.getClass());
                        // Include the primary key of this Firebase data record. Named it 'recKeyID'
                        fldObj.put("recKeyID", shot.getKey());
//                    ans = shot.child("user type").getValue().toString();
                        list.add(fldObj);

                        ans = list.get(0).get("myWorkTypes").toString();

                        Log.d("types", ans);

                        call.onCallback(fldObj.get("myWorkTypes"));

                    } catch (Exception ex) {


                        continue;
                    }


                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }


    public void checkBoxOn(String typeName) {
        //Set checkBox on, by user profile data.

        if (typeName.equals("צילומי זוגיות ומשפחה")) {
            family.setChecked(true);
        }

        if (typeName.equals("צילומי וידאו")) {
            video.setChecked(true);
        }

        if (typeName.equals("צילום אובייקטים")) {
            objects.setChecked(true);
        }
        if (typeName.equals("צילום אירועים")) {
            events.setChecked(true);
        }
        if (typeName.equals("מגנטים לאירועים")) {
            magnets.setChecked(true);
        }
        if (typeName.equals("צילומי סטודיו")) {
            studio.setChecked(true);
        }
        if (typeName.equals("צילומי רחפן")) {
            copter.setChecked(true);
        }

    }

}


