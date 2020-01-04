package com.example.phototor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ArrayAdapter;


import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;


public class photographersList extends AppCompatActivity {

    DatabaseReference dbRef;
    ListView list;
    ArrayList <String> arrList= new ArrayList<>();
    ArrayList <String> photographersIDs = new ArrayList<>();
    ArrayAdapter <String> arrAdp;
    SharedPreferences sp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photographers_list);
        dbRef = FirebaseDatabase.getInstance().getReference("users");
        list = (ListView) findViewById(R.id.listView);
        arrAdp= new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,arrList);
        list.setAdapter(arrAdp);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Log.d("ITEM", Integer.toString(position));
                Log.d("details", arrList.get(position));
                String userID = photographersIDs.get(position);
                Log.d("userID", userID );

                profileDisplay(userID);

            }
        });

        dbRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {


                try {
                    Object fieldsObj = new Object();
                    ArrayList<HashMap<String, Object>> list = new ArrayList<>();

                    HashMap fldObj = (HashMap)dataSnapshot.getValue(fieldsObj.getClass());

                    fldObj.put("recKeyID", dataSnapshot.getKey());
                    String userType = fldObj.get("userType").toString();
                    list.add(fldObj);

                    Log.d("USER-Hash", fldObj.toString());
                    String value= dataSnapshot.getValue(User.class).toString();

                    if(userType.equals("photographer")) {
                        String userId = fldObj.get("recKeyID").toString();
                        arrList.add(value);
                        photographersIDs.add(userId);
                        Collections.sort(arrList);
                        arrAdp.notifyDataSetChanged();
                    }
                }
                catch (Exception ex){

                }



            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }


    public void profileDisplay(String userId){

        Intent intent=new Intent(this,photographerProfileDisplay.class);
        intent.putExtra("id", userId);
        startActivity(intent);

    }


}
