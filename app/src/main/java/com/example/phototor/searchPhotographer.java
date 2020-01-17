package com.example.phototor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class searchPhotographer extends AppCompatActivity implements View.OnClickListener {

    Button send;
    Button orderDetails;
    DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
    EditText userName;
    Spinner areas;

    EditText date;
    EditText time;
    EditText place;
    EditText notes;
    Button confrim;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_photographer);

        send = (Button) findViewById(R.id.searchBtn);
        areas = (Spinner) findViewById(R.id.areasSppiner);
        orderDetails = (Button) findViewById(R.id.orderDetails);
        date = (EditText) findViewById(R.id.dateET);
        time = (EditText) findViewById(R.id.startTimeET);
        notes = (EditText) findViewById(R.id.notesET);
        confrim = (Button) findViewById(R.id.confrimBtn);
        place = (EditText) findViewById(R.id.locationET);

    }

    @Override
    public void onClick(View view) {

        if(view == send){

            String area = areas.getSelectedItem().toString();
            Log.d("SEARCH!", area);
            searchPhotographer(new MyCallback() {
                @Override
                public void onCallback(Object value) {



//                    Log.d("170120", value.toString());
                    displayResults(value);
                }
            } ,area);


        }

        if(view == orderDetails ){

            date.setVisibility(View.VISIBLE);
            time.setVisibility(View.VISIBLE);
            notes.setVisibility(View.VISIBLE);
            confrim.setVisibility(View.VISIBLE);
            place.setVisibility(View.VISIBLE);

        }


        if(view == confrim ){

            date.setEnabled(false);
            time.setEnabled(false);
            notes.setEnabled(false);
            confrim.setEnabled(false);
            place.setEnabled(false);

        }






    }


    public void searchPhotographer(final MyCallback call, final String area){

        dbRef = FirebaseDatabase.getInstance().getReference("areas");

        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                try {

                    Object fieldsObj = new Object();
                    ArrayList<HashMap<String, Object>> list = new ArrayList<>();
                    HashMap fldObj = (HashMap)dataSnapshot.getValue(fieldsObj.getClass());
                    HashMap areaResults =(HashMap)fldObj.get(area);






                    call.onCallback(areaResults);

                }
                catch (Exception ex){

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

    }

    public void displayResults(Object results){

        Intent intent=new Intent(this,searchPhotographersResults.class);
        String area = areas.getSelectedItem().toString();
        String orderDate = date.getText().toString();
        String orderTime = time.getText().toString();
        String orderLocation = place.getText().toString();
        String orderNotes = notes.getText().toString();



        intent.putExtra("results", (HashMap)results);
        intent.putExtra("area", area);
        intent.putExtra("date", orderDate);
        intent.putExtra("time", orderTime);
        intent.putExtra("location", orderLocation);
        intent.putExtra("notes", orderNotes);
        startActivity(intent);

    }







}
