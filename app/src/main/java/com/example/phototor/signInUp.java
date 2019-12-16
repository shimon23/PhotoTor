package com.example.phototor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class signInUp extends AppCompatActivity  implements View.OnClickListener {

    Button login;
    Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_up);

        login=(Button)findViewById(R.id.btnlogin);
        register=(Button)findViewById(R.id.btnregister);

    }

    @Override
    public void onClick(View view) {

        if(view==login){
            startActivity(new Intent(this, login.class));
            finish();
        }
        if(view==register){
            startActivity(new Intent(this, register.class));
            finish();
        }


    }
}
