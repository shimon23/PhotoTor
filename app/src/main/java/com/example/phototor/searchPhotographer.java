package com.example.phototor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class searchPhotographer extends AppCompatActivity implements View.OnClickListener {

    Button send;
    DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
    EditText userName;
    Spinner areas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_photographer);

        send = (Button) findViewById(R.id.searchBtn);
        userName = (EditText) findViewById(R.id.userText);
        areas = (Spinner) findViewById(R.id.areasSppiner);
    }

    @Override
    public void onClick(View view) {

        if(view == send){

            String area = areas.getSelectedItem().toString();
            Log.d("SEARCH!", area);
            findTest("areas",area);
//
//            final Map<String, Object> dataMap = new HashMap<String, Object>();
//            dataMap.put("netanel", "hugi");
//
//            dbRef.child("orders").updateChildren(dataMap).addOnSuccessListener(new OnSuccessListener<Void>() {
//                @Override
//                public void onSuccess(Void aVoid) {
////                    progressDialog.dismiss();
////                    next.setClickable(true);
////                    next.setVisibility(View.VISIBLE);
//                }
//            });


        }



    }


    public void findTest(final String dbName, final String op){

        dbRef = FirebaseDatabase.getInstance().getReference(dbName);

        dbRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {


                try {

                    Object fieldsObj = new Object();
                    ArrayList<HashMap<String, Object>> list = new ArrayList<>();
                    HashMap fldObj = (HashMap)dataSnapshot.getValue(fieldsObj.getClass());

                    Log.d("findTest", fldObj.toString());

//
//                    fldObj.put("recKeyID", dataSnapshot.getKey());
//                    String userType = fldObj.get(op).toString();
//                    list.add(fldObj);
//
//                    Log.d("USER-Hash", fldObj.toString());
//                    String value= dataSnapshot.getValue(User.class).toString();
//
//                    if(userType.equals("photographer")) {
//                        String userId = fldObj.get("recKeyID").toString();
//                    }
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
}
