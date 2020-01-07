package com.example.phototor;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import static com.example.phototor.R.id.bottom;
import static com.example.phototor.R.id.mangCalendarbtn;
import static com.example.phototor.R.id.workTypebtn;

public class photographerMenu extends AppCompatActivity  implements  AdapterView.OnItemSelectedListener ,View.OnClickListener {

    Button logOut;
    private FirebaseAuth mAuth;
    Button Area;
    TextView tvArea;
    String[] listArea;
    boolean[]checkArea;
    ArrayList<Integer> mSlecterdArea =new ArrayList<>();
   // Button mangeClendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photographer_menu);
        Area = (Button) findViewById(R.id.btnArea);
        tvArea = (TextView) findViewById(R.id.tvAreaSelected);

        mAuth = FirebaseAuth.getInstance();

        listArea = getResources().getStringArray(R.array.Area);
        checkArea = new boolean[listArea.length];

        logOut = (Button)findViewById(R.id.logoutBtn);
        Button mangeClendar =  findViewById(mangCalendarbtn);
        mangeClendar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), CalendarActivity.class);
                startActivityForResult(myIntent, 0);

            }});
        Button TypeWork = findViewById(workTypebtn);
        TypeWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), activity_tepyofwork.class);
                startActivityForResult(myIntent, 0);
            }
        });
        Button area = findViewById(R.id.btnArea);
        area.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), activity_area.class);
                startActivityForResult(myIntent, 0);
            }
        });





    }


    @Override
    public void onClick(View view) {

        if(view==logOut) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }




    }




    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text = adapterView.getItemAtPosition(i).toString();
        Toast.makeText(adapterView.getContext() , text,Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
