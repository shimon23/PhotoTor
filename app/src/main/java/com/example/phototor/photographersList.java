package com.example.phototor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.ArrayAdapter;


import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;


public class photographersList extends AppCompatActivity {

    DatabaseReference dbRef;
    ListView list;
    ArrayList <String> arrList= new ArrayList<>();
    ArrayAdapter <String> arrAdp;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photographers_list);
        dbRef = FirebaseDatabase.getInstance().getReference("users");
        list = (ListView) findViewById(R.id.listView);
        arrAdp= new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,arrList);
        list.setAdapter(arrAdp);

        dbRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                Object fieldsObj = new Object();
                ArrayList<HashMap<String, Object>> list = new ArrayList<>();

                String userKey = dataSnapshot.getKey();
                HashMap fldObj = (HashMap)dataSnapshot.getValue(fieldsObj.getClass());

                fldObj.put("recKeyID", dataSnapshot.getKey());
                list.add(fldObj);

                Log.d("USER-Hash", fldObj.toString());

//                str = list.get(0).toString();
                String value= dataSnapshot.getValue(User.class).toString();

//                arrList.add(fldObj.toString());
                arrList.add(value);
                arrAdp.notifyDataSetChanged();




//                for (DataSnapshot shot : dataSnapshot.getChildren()){
//
//                    String str = shot.getKey();
//                    arrList.add(str);
//                    arrAdp.notifyDataSetChanged();
//
//
//                }

//                String value = dataSnapshot.getValue(photographer.class).toString();



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
