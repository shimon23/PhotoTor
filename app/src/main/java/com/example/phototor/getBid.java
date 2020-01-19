package com.example.phototor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class getBid extends AppCompatActivity implements View.OnClickListener {

    FirebaseAuth mAuth;
    DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();


    String area;
    String date;
    String time;
    String location;
    String notes;
    String photographerID;
    int eventid;
    String clientID;

    EditText dateET;
    EditText timeET;
    EditText locationET;
    EditText notesET;
    Spinner eventsTypes;

    Button confrim;
    Button returnToMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_bid);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
//        editor.putString("orderID","10000");
//        editor.apply();



        mAuth = FirebaseAuth.getInstance();

        dateET = (EditText)findViewById(R.id.dateET);
        timeET = (EditText)findViewById(R.id.timeET);
        locationET = (EditText)findViewById(R.id.locationET);
        notesET = (EditText)findViewById(R.id.notesET);
        eventsTypes = (Spinner) findViewById(R.id.eventsTypesSppiner);
        confrim = (Button) findViewById(R.id.confrimBtn);
        returnToMenu = (Button) findViewById(R.id.returnBtn);
        clientID = mAuth.getUid();


        Intent intent = getIntent();
        photographerID = intent.getExtras().getString("id");
        area = intent.getExtras().getString("area");
        date = intent.getExtras().getString("date");
        time = intent.getExtras().getString("time");
        location = intent.getExtras().getString("location");
        notes = intent.getExtras().getString("notes");
        eventid = intent.getExtras().getInt("eventid");


        eventsTypes.setSelection(eventid);


        if(date != null){
            dateET.setText(date);
        }

        if(time != null){
            timeET.setText(time);
        }

        if(location != null){
            locationET.setText(location);
        }

        if(notes != null){
            notesET.setText(notes);
        }


    }

    @Override
    public void onClick(View view) {

        if(view == confrim){


            getLastOrderID(new MyCallback() {
                @Override
                public void onCallback(Object value) {
                    makeOrder(Integer.parseInt(value.toString())+1);

                }
            });

            dateET.setEnabled(false);
            timeET.setEnabled(false);
            locationET.setEnabled(false);
            eventsTypes.setEnabled(false);
            notesET.setEnabled(false);
            returnToMenu.setVisibility(View.VISIBLE);
            confrim.setEnabled(false);

        }

        if(view == returnToMenu){
            Intent intent=new Intent(this,clientMenu.class);
            startActivity(intent);

        }

    }

    public void makeOrder(int orderID) {
        date = dateET.getText().toString();
        time = timeET.getText().toString();
        location = locationET.getText().toString();
        String eventType = eventsTypes.getSelectedItem().toString();
        notes = notesET.getText().toString();

        Order bid = new Order(orderID,photographerID,clientID,date,time,eventType,location,notes);


        final Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put(Integer.toString(orderID), bid.toMap());

        updateOrdersTree(orderID,dataMap);
        updateUserProfile(photographerID,orderID,dataMap);
        updateUserProfile(clientID,orderID,dataMap);


    }

    public void updateOrdersTree(int orderID, Map orderData){
        dbRef.child("orders").updateChildren(orderData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        });
    }

    public void updateUserProfile(String userID,int orderID, Map orderData) {

        dbRef.child("users").child(userID).child("myOrders").updateChildren(orderData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        });

    }

    public void getLastOrderID(final MyCallback myCallback){



        dbRef.child("orders").orderByKey().limitToLast(1).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                    String latestKey = childSnapshot.getKey();
                    myCallback.onCallback(latestKey);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
