package com.example.phototor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class searchPhotographer extends AppCompatActivity implements View.OnClickListener {

    Button send;
    DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
    EditText userName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_photographer);

        send = (Button) findViewById(R.id.sendBtn);
        userName = (EditText) findViewById(R.id.userText);
    }

    @Override
    public void onClick(View view) {

        if(view == send){
            Log.d("SEARCH", userName.getText().toString());

            final Map<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("netanel", "hugi");

            dbRef.child("orders").updateChildren(dataMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
//                    progressDialog.dismiss();
//                    next.setClickable(true);
//                    next.setVisibility(View.VISIBLE);
                }
            });


        }



    }
}
