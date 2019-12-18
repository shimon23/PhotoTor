package com.example.phototor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import android.content.Intent;



public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() != null)
        {
            startActivity(new Intent(this, signInUp.class));
            finish();
        }
        else
        {
            authenticateUser();
        }

    }

    private void authenticateUser(){

//        List<AuthUI.IdpConfig> providers = Arrays.asList(new AuthUI.IdpConfig.EmailBuilder().build());
//        startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder()
//                .setAvailableProviders(providers)
//                .build(), REQUEST_CODE);
        int i = 5;
    }



}
