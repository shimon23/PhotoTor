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
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class myOrders extends AppCompatActivity {

    DatabaseReference dbRef;
    ArrayList<String> arrList = new ArrayList<>();
    ArrayList<String> photographersIDs = new ArrayList<>();
    ArrayAdapter<String> arrAdp;
    ListView lv;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incoming_orders);
        dbRef = FirebaseDatabase.getInstance().getReference("users");


        arrAdp = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, arrList);
        lv = (ListView) findViewById(R.id.myOrdersLV);
        lv.setAdapter(arrAdp);

        getUserOrders(new MyCallback() {
            @Override
            public void onCallback(Object value) {

                HashMap netanel = ((HashMap) value);

                arrList = new ArrayList<String>(netanel.keySet());
                display(arrList);


            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {


                String orderId = arrList.get(position);
                displayOrder(orderId);


            }
        });


    }

    public void displayOrder(String orderID) {
        Intent intent = new Intent(this, displayOrder.class);
        intent.putExtra("orderID",orderID);
        startActivity(intent);
    }

    public void display(Object list) {
        arrAdp = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, (List<String>) list);
        lv = (ListView) findViewById(R.id.myOrdersLV);
        lv.setAdapter(arrAdp);

    }

    public void getUserOrders(final MyCallback call) {

        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                String ans = "";

                ArrayList<HashMap<String, Object>> list = new ArrayList<>();
                if (dataSnapshot == null) {

                    call.onCallback("null");
                }

                Object fieldsObj = new Object();
                HashMap fldObj;


                try {

                    fldObj = (HashMap) dataSnapshot.getValue(fieldsObj.getClass());
                    fldObj.put("recKeyID", dataSnapshot.getKey());
                    list.add(fldObj);
                    HashMap user = (HashMap) fldObj.get(mAuth.getUid());

                    call.onCallback(user.get("myOrders"));

                } catch (Exception ex) {

                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }
}
