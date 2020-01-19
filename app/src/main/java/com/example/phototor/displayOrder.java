package com.example.phototor;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class displayOrder extends AppCompatActivity implements View.OnClickListener {

    Button confrimBtn;
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

    Dialog d;
    Button dialogConfirm;
    EditText dialogPrice;

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
        confrimBtn = (Button)findViewById(R.id.confrimBtn);
        cancel = (Button)findViewById(R.id.cancelBtn);



        // get user areas from database and turn the checkBox on:
        getOrderDetais(new MyCallback() {
            @Override
            public void onCallback(Object value) {

                Map<String, Object> dataMap = (HashMap) value;

                photographerID = dataMap.get("photographerID").toString();
                clientID = dataMap.get("clientID").toString();

                String dateStr = "תאריך האירוע:" + " "+ dataMap.get("date").toString();
                date.setText(dateStr);

                String timeStr = "שעת האירוע:" + " "+ dataMap.get("time").toString();
                time.setText(timeStr);

                String locationStr = "מקום האירוע:" + " "+ dataMap.get("location").toString();
                location.setText(locationStr);

                String notesStr = "הערות:" + " "+ dataMap.get("notes").toString();
                notes.setText(notesStr);

                String typeStr = "סוג האירוע:" + " "+ dataMap.get("eventType").toString();
                type.setText(typeStr);

                String priceStr = "מחיר:" + " "+ dataMap.get("price").toString();
                price.setText(priceStr);

                String statusStr = "סטטוס הזמנה:" + " "+ dataMap.get("status").toString();
                status.setText(statusStr);

                if(dataMap.get("status").toString().equals("מחכה להצעת מחיר") && mAuth.getUid().equals(photographerID)){
                    priceBtn.setVisibility(View.VISIBLE);
                    confrimBtn.setEnabled(false);
                }

                if(dataMap.get("status").toString().equals("התקבלה הצעת מחיר") && mAuth.getUid().equals(photographerID)){
                    confrimBtn.setEnabled(false);
                }

                if(dataMap.get("status").toString().equals("מבוטל") && mAuth.getUid().equals(photographerID)){
                    confrimBtn.setEnabled(false);
                    cancel.setEnabled(false);
                    price.setVisibility(View.INVISIBLE);
                }

                if(dataMap.get("status").toString().equals("מאושר")){
                    confrimBtn.setEnabled(false);
                    cancel.setEnabled(true);
                    price.setVisibility(View.INVISIBLE);
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

    public void createPriceDialog(){

        d = new Dialog(this);
        d.setContentView(R.layout.pricedialog);
        d.setTitle("הצעת מחיר");
        d.setCancelable(true);

        dialogConfirm = (Button)d.findViewById(R.id.confrimBtn);
        dialogPrice = (EditText) d.findViewById(R.id.priceET);
        dialogConfirm.setOnClickListener(this);

        d.show();

    }

    @Override
    public void onClick(View view) {

        if(view == priceBtn){
            createPriceDialog();
        }

        if(view == dialogConfirm){
            Log.d("180120", "dialog");
            d.dismiss();
            priceBtn.setEnabled(false);

            updateOrder(new MyCallback() {
                @Override
                public void onCallback(Object value) {


                        }

            },"price",dialogPrice.getText().toString());

            updateOrder(new MyCallback() {
                @Override
                public void onCallback(Object value) {


                }

            },"status","התקבלה הצעת מחיר");

            Intent intent = new Intent(this, myOrders.class);
            startActivity(intent);
        }

        if(view == cancel){

            updateOrder(new MyCallback() {
                @Override
                public void onCallback(Object value) {


                }

            },"status","מבוטל");

            confrimBtn.setEnabled(false);
            cancel.setEnabled(false);
            Intent intent = new Intent(this, myOrders.class);
            startActivity(intent);

        }

        if(view == confrimBtn){

            updateOrder(new MyCallback() {
                @Override
                public void onCallback(Object value) {


                }

            },"status","מאושר");

            Intent intent = new Intent(this, myOrders.class);
            startActivity(intent);

        }

    }

    public void updateOrder(final MyCallback call, final String key, final String value){

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

                    order.put(key,value);

                    dbRef.child("orders").child(orderID).updateChildren(order).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                        }
                    });

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
