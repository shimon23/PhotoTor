package com.example.phototor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class clientMenu extends AppCompatActivity implements View.OnClickListener {

    Button logOut;
    Button photographersList;
    Button search;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_menu);

        logOut = (Button)findViewById(R.id.logoutBtn);
        photographersList = (Button) findViewById(R.id.photographersListBtn);
        search = (Button) findViewById(R.id.searchBtn);





    }


    @Override
    public void onClick(View view) {

        if(view==logOut){
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }

        if(view==photographersList){
            startActivity(new Intent(this, photographersList.class));
            finish();
        }

        if(view == search ){
            startActivity(new Intent(this, searchPhotographer.class));
            finish();
        }
    }
}
