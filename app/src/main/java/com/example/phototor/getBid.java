package com.example.phototor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Spinner;

public class getBid extends AppCompatActivity {

    String area;
    String date;
    String time;
    String location;
    String notes;
    String photographerID;
    int eventid;

    EditText dateET;
    EditText timeET;
    EditText locationET;
    EditText notesET;
    Spinner eventsTypes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_bid);

        dateET = (EditText)findViewById(R.id.dateET);
        timeET = (EditText)findViewById(R.id.timeET);
        locationET = (EditText)findViewById(R.id.locationET);
        notesET = (EditText)findViewById(R.id.notesET);
        eventsTypes = (Spinner) findViewById(R.id.eventsTypesSppiner);


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
}
