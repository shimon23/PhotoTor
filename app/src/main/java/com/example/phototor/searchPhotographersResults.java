package com.example.phototor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class searchPhotographersResults extends AppCompatActivity {

    ListView lv;
    ArrayAdapter<String> arrAdp;
    String area;
    String date;
    String time;
    String location;
    String notes;
    int eventid;

    DatabaseReference dbRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_photographers_results);

        Intent intent = getIntent();
        ArrayList photographersID = (ArrayList) intent.getSerializableExtra("results");
        area = intent.getExtras().getString("area");
        date = intent.getExtras().getString("date");
        time = intent.getExtras().getString("time");
        location = intent.getExtras().getString("location");
        notes = intent.getExtras().getString("notes");
        eventid = intent.getExtras().getInt("eventid");



        final List<String> keys = new ArrayList<>(photographersID);
        idsToNames(keys);



        dbRef = FirebaseDatabase.getInstance().getReference("users");

        arrAdp = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, keys);
        lv = (ListView) findViewById(R.id.listView10);
        lv.setAdapter(arrAdp);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Log.d("ITEM", Integer.toString(position));
                Log.d("details", keys.get(position));
                String userID = keys.get(position);
                Log.d("userID", userID);

                profileDisplay(userID);

            }
        });




    }

    public void profileDisplay(String userId) {

        Intent intent = new Intent(this, photographerProfileDisplay.class);
        intent.putExtra("id", userId);
        intent.putExtra("area", area);
        intent.putExtra("date", date);
        intent.putExtra("time", time);
        intent.putExtra("location", location);
        intent.putExtra("notes", notes);
        intent.putExtra("eventid", eventid);

        startActivity(intent);

    }


    public List<String> idsToNames(final List<String> ids) {

        final List<String> ans = new ArrayList<>();

        idToName(new MyCallback() {
            @Override
            public void onCallback(Object value) {
                Log.d("170120", "test");

                display(value);

            }
        } ,ids);

        return ans;


    }

    public void display(Object list){
        arrAdp = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, (List<String>) list);
        lv = (ListView) findViewById(R.id.listView10);
        lv.setAdapter(arrAdp);

    }


    public void idToName(final MyCallback call,final List<String> ids){



        dbRef = FirebaseDatabase.getInstance().getReference();


        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                try {

                    List<String> ans = new ArrayList<>();


                    for(String id: ids){
                        Object fieldsObj = new Object();
                        HashMap fldObj = (HashMap)dataSnapshot.getValue(fieldsObj.getClass());
                        HashMap results =(HashMap)((HashMap)fldObj.get("users")).get(id);

                        String fullName = results.get("firstName") + " " + results.get("lastName");
                        ans.add(fullName);


                    }

                    call.onCallback(ans);



                }
                catch (Exception ex){
                    Log.d("170120", "error");

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });



    }

}
