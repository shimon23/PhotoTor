package com.example.phototor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class displayOrder extends AppCompatActivity {

    Button confrim;
    Button cancel;

    TextView orderNumber;
    TextView photoName;
    TextView photoNumber;
    TextView clientName;
    TextView clientNumber;

    TextView date;
    TextView time;
    TextView location;
    TextView notes;
    TextView type;
    TextView status;
    TextView price;

    String orderID;

    DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_order);

        Intent intent = getIntent();
        orderID = intent.getExtras().getString("orderID");

        orderNumber = (TextView) findViewById(R.id.orderNumTV);
        orderNumber.setText("מספר הזמנה:" + " " + orderID);

        photoName = (TextView) findViewById(R.id.photoName);

        // get user areas from database and turn the checkBox on:
        getOrderDetais(new MyCallback() {
            @Override
            public void onCallback(Object value) {

                Map<String, Object> dataMap = (HashMap) value;

                photoName.setText(dataMap.get("photographerID").toString());




            }
        });

    }

    public void getOrderDetais(final MyCallback call) {

        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//                Log.d("areas", ans);

                String ans = "";

                ArrayList<HashMap<String, Object>> list = new ArrayList<>();
                if (dataSnapshot == null) {
                    call.onCallback("null");
                }

                Object fieldsObj = new Object();
                HashMap fldObj;


                try {

                    fldObj = (HashMap) dataSnapshot.getValue(fieldsObj.getClass());
                    HashMap orders = (HashMap) fldObj.get("orders");
                    HashMap order = (HashMap) orders.get(orderID);

                    call.onCallback(order);

                } catch (Exception ex) {

                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }
}
