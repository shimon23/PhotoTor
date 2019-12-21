package com.example.phototor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class photographerMenu extends AppCompatActivity  implements  AdapterView.OnItemSelectedListener ,View.OnClickListener {

    Button logOut;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photographer_menu);

        mAuth = FirebaseAuth.getInstance();


    }


    @Override
    public void onClick(View view) {
        Spinner spinner = findViewById(R.id.Area);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Area ,R.layout.activity_photographer_menu );
        adapter.setDropDownViewResource((R.layout.support_simple_spinner_dropdown_item ));
        spinner.setAdapter(adapter);
        spinner.setOnItemClickListener(this);
        if(view==logOut){
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
