package com.example.phototor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class photographerProfileDisplay extends AppCompatActivity {

    TextView tv;

    DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photographer_profile_display);

        Intent intent = getIntent();
        String photographerID = intent.getExtras().getString("id");


        getUserData(new MyCallback() {
            @Override
            public void onCallback(String value) {
                Log.d("USER", value);
//                startMenu(value);
            }
        });



    }


    public void getUserData(final MyCallback call) {

        Intent intent = getIntent();
        final String photographerID = intent.getExtras().getString("id");

        dbRef = FirebaseDatabase.getInstance().getReference("users");

        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                String ans = "";

                ArrayList<HashMap<String, Object>> list = new ArrayList<>();
                if (dataSnapshot == null){
                    call.onCallback("null");
                }

//              This is awesome! You don't have to know the data structure of the database.
                Object fieldsObj = new Object();
                HashMap fldObj;

//                DataSnapshot ds = dataSnapshot.child(photographerID);

                for (DataSnapshot shot : dataSnapshot.getChildren()){

//                    shot = shot.child(photographerID);


                    try{

                        Log.d("photoID", photographerID);
                        fldObj = (HashMap)shot.getValue(fieldsObj.getClass());
                        fldObj.put("recKeyID", shot.getKey());
                        list.add(fldObj);

                        ans = list.get(0).toString();

                        Log.d("userData", fldObj.toString());

                        tv = (TextView) findViewById(R.id.textView4);
                        tv.setText("welcome " + fldObj.get("city"));

                        call.onCallback(ans);

                    }catch (Exception ex){


                        continue;
                    }



                }



//                String value = dataSnapshot.getValue(String.class);
//                call.onCallback(value);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });


    }

}




