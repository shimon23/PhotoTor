package com.example.phototor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
    Button priceBtn;

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
    String photographerID;
    String clientID;

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
        photoNumber = (TextView) findViewById(R.id.photoPhone);
        clientName = (TextView) findViewById(R.id.clientName);
        clientNumber = (TextView) findViewById(R.id.clientPhone);

        date = (TextView) findViewById(R.id.orderDate);
        time = (TextView) findViewById(R.id.orderTime);
        location = (TextView) findViewById(R.id.locOrder);
        notes = (TextView) findViewById(R.id.notesOrder);
        type = (TextView) findViewById(R.id.typeOrder);
        status = (TextView) findViewById(R.id.statusOrder);
        price = (TextView) findViewById(R.id.priceOrder);

        priceBtn = (Button)findViewById(R.id.priceBtn);
        confrim = (Button)findViewById(R.id.confrimBtn);



        // get user areas from database and turn the checkBox on:
        getOrderDetais(new MyCallback() {
            @Override
            public void onCallback(Object value) {

                Map<String, Object> dataMap = (HashMap) value;

                photographerID = dataMap.get("photographerID").toString();
                clientID = dataMap.get("clientID").toString();

                Log.d("180120-phone",dataMap.toString());


                date.setText(dataMap.get("date").toString());
                time.setText(dataMap.get("time").toString());
                location.setText(dataMap.get("location").toString());
                notes.setText(dataMap.get("notes").toString());
                type.setText(dataMap.get("eventType").toString());
                status.setText(dataMap.get("status").toString());
//                price.setText(dataMap.get("price").toString());

                Log.d("180120-price",mAuth.getUid());
                Log.d("180120-price",photographerID);

                if(status.getText().toString().equals("מחכה להצעת מחיר") && mAuth.getUid().equals(photographerID)){
                    priceBtn.setVisibility(View.VISIBLE);
                    confrim.setEnabled(false);

                }


            }
        });

        getPhotographerDeatils(new MyCallback() {
            @Override
            public void onCallback(Object value) {

                Map<String, Object> dataMap = (HashMap) value;

                String photoNameStr = dataMap.get("firstName").toString() + " " + dataMap.get("lastName").toString();
                photoName.setText(photoNameStr);

                String photoPhone = dataMap.get("phoneNumber").toString();
                photoNumber.setText(photoPhone);


            }
        });

        getClientDeatils(new MyCallback() {
            @Override
            public void onCallback(Object value) {

                Map<String, Object> dataMap = (HashMap) value;

                String photoNameStr = dataMap.get("firstName").toString() + " " + dataMap.get("lastName").toString();
                clientName.setText(photoNameStr);

                String photoPhone = dataMap.get("phoneNumber").toString();
                Log.d("180120-phone",photoPhone);
                clientNumber.setText(photoPhone);

            }
        });



    }

    public void getOrderDetais(final MyCallback call) {

        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

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

    public void getPhotographerDeatils(final MyCallback call) {

        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ArrayList<HashMap<String, Object>> list = new ArrayList<>();
                if (dataSnapshot == null) {
                    call.onCallback("null");
                }

                Object fieldsObj = new Object();
                HashMap fldObj;


                try {

                    fldObj = (HashMap) dataSnapshot.getValue(fieldsObj.getClass());
                    HashMap users = (HashMap) fldObj.get("users");
                    HashMap user = (HashMap) users.get(photographerID);

                    call.onCallback(user);

                } catch (Exception ex) {

                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }

    public void getClientDeatils(final MyCallback call) {

        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ArrayList<HashMap<String, Object>> list = new ArrayList<>();
                if (dataSnapshot == null) {
                    call.onCallback("null");
                }

                Object fieldsObj = new Object();
                HashMap fldObj;


                try {

                    fldObj = (HashMap) dataSnapshot.getValue(fieldsObj.getClass());
                    HashMap users = (HashMap) fldObj.get("users");
                    HashMap user = (HashMap) users.get(clientID);

                    call.onCallback(user);

                } catch (Exception ex) {

                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }
}
