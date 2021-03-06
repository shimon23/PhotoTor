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

public class activity_area extends AppCompatActivity implements View.OnClickListener {

    CheckBox north;
    CheckBox sharon;
    CheckBox shomron;
    CheckBox jerusalem;
    CheckBox south;
    CheckBox central;
    Button save;

    DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area);

        north = (CheckBox) findViewById(R.id.familyChkBox);
        sharon = (CheckBox) findViewById(R.id.videoChkBox);
        shomron = (CheckBox) findViewById(R.id.objectsChkBox);
        jerusalem = (CheckBox) findViewById(R.id.eventsChkBox);
        south = (CheckBox) findViewById(R.id.magnetsChkBox);
        central = (CheckBox) findViewById(R.id.studioChkBox);
        save = (Button) findViewById(R.id.saveBtn);

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
            addOrRemoveArea(north, "צפון");
            addOrRemoveArea(sharon, "שרון");
            addOrRemoveArea(shomron, "שומרון");
            addOrRemoveArea(jerusalem, "ירושלים");
            addOrRemoveArea(south, "דרום");
            addOrRemoveArea(central, "מרכז");
            updateProfile();

            Toast.makeText(getApplicationContext(), "נשמר בהצלחה!",
                    Toast.LENGTH_SHORT).show();

        }

    }


    public void addOrRemoveArea(CheckBox areaBtn, String areaName) {
        //If the user choose area: add the userID to area child in areas database.
        //Else: remove the userID from area child in areas database.

        String userID = mAuth.getUid();

        if (areaBtn.isChecked()) {
            final Map<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put(userID, "yes");
            dbRef.child("areas").child(areaName).updateChildren(dataMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {

                }
            });
        } else if (areaBtn.isChecked() == false) {
            dbRef.child("areas").child(areaName).child(userID).removeValue();
        }


    }

    public void updateProfile() {
        //update the userID child to "users" database.

        Map<String, Object> dataMap = new HashMap<String, Object>();
        String userID = mAuth.getUid();

        dataMap = updateAreasMap(dataMap, north, "צפון");
        dataMap = updateAreasMap(dataMap, sharon, "שרון");
        dataMap = updateAreasMap(dataMap, shomron, "שומרון");
        dataMap = updateAreasMap(dataMap, jerusalem, "ירושלים");
        dataMap = updateAreasMap(dataMap, south, "דרום");
        dataMap = updateAreasMap(dataMap, central, "מרכז");


        dbRef.child("users").child(userID).child("myAreas").updateChildren(dataMap).addOnSuccessListener(new OnSuccessListener<Void>() {
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

                        ans = list.get(0).get("myAreas").toString();


                        call.onCallback(fldObj.get("myAreas"));

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


    public void checkBoxOn(String areaName) {
        //Set checkBox on, by user profile data.

        if (areaName.equals("צפון")) {
            north.setChecked(true);
        }

        if (areaName.equals("שרון")) {
            sharon.setChecked(true);
        }

        if (areaName.equals("שומרון")) {
            shomron.setChecked(true);
        }
        if (areaName.equals("ירושלים")) {
            jerusalem.setChecked(true);
        }
        if (areaName.equals("דרום")) {
            south.setChecked(true);
        }
        if (areaName.equals("מרכז")) {
            central.setChecked(true);
        }

    }

}

