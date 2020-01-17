package com.example.phototor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class searchPhotographersResults extends AppCompatActivity {

    ListView lv;
    ArrayAdapter <String> arrAdp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_photographers_results);

        Intent intent = getIntent();
        HashMap photographersID = (HashMap<String, String>) intent.getSerializableExtra("results");

        final List<String> keys = new ArrayList<>(photographersID.keySet());

        Log.d("170120", keys.toString());

        arrAdp= new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,keys);
        lv = (ListView) findViewById(R.id.listView10);
        lv.setAdapter(arrAdp);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Log.d("ITEM", Integer.toString(position));
                Log.d("details", keys.get(position));
                String userID = keys.get(position);
                Log.d("userID", userID );

                profileDisplay(userID);

            }
        });



    }

    public void profileDisplay(String userId){

        Intent intent=new Intent(this,photographerProfileDisplay.class);
        intent.putExtra("id", userId);
        startActivity(intent);

    }
}
