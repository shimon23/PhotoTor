package com.example.phototor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.AuthResult;
import com.google.android.gms.tasks.OnCompleteListener;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;

    Button login;
    Button register;
    EditText enterMail;
    EditText enterPass;
    private TextView mStatusTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        login = (Button) findViewById(R.id.loginBtn);
        register = (Button) findViewById(R.id.registerBtn);

        enterMail = (EditText) findViewById(R.id.enterMail);
        enterPass = (EditText) findViewById(R.id.enterPass);

        if (mAuth.getCurrentUser() != null)
        {
            startActivity(new Intent(this, photographerMenu.class));
            finish();
        }
//        else
//        {
//            authenticateUser();
//        }

    }

    private void login(){

        String email = enterMail.getText().toString();
        String password = enterPass.getText().toString();

        if(!email.isEmpty() && !password.isEmpty()){
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                Toast.makeText(getApplicationContext(), "התחברת בהצלחה!",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "ההתחברות נכשלה!",
                                        Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
        }



    }


    @Override
    public void onClick(View view) {

        if(view==login){
            login();

            if(mAuth.getCurrentUser() != null){

                Toast.makeText(getApplicationContext(), mAuth.getCurrentUser().getEmail(),
                        Toast.LENGTH_SHORT).show();

                startActivity(new Intent(this, photographerMenu.class));
                finish();
            }


        }
        if(view==register){
            startActivity(new Intent(this, register.class));
            finish();
        }


    }







}
