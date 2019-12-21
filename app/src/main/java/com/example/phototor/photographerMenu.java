package com.example.phototor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class photographerMenu extends AppCompatActivity  implements View.OnClickListener {

    Button logOut;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photographer_menu);

        logOut = (Button)findViewById(R.id.logOutBtn);
        mAuth = FirebaseAuth.getInstance();


    }

    @Override
    public void onClick(View view) {
        if(view==logOut){
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }
}
