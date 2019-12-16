package com.example.phototor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Context;


import com.google.firebase.auth.FirebaseAuth;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.android.gms.tasks.OnCompleteListener;
import androidx.annotation.NonNull;


public class register extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    Button save;
    EditText editPass;
    EditText editMail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        save =(Button)findViewById(R.id.btnregister);

        editMail = (EditText) findViewById(R.id.editEmail);
        editPass = (EditText) findViewById(R.id.editPass);


    }

    private void registerUser(){

        //getting email and password from edit texts
        String mail = editMail.getText().toString();
        String pass = editPass.getText().toString();

//        //checking if email and passwords are empty
//        if(TextUtils.isEmpty(email)){
//            Toast.makeText(this,"Please enter email",Toast.LENGTH_LONG).show();
//            return;
//        }
//
//        if(TextUtils.isEmpty(password)){
//            Toast.makeText(this,"Please enter password",Toast.LENGTH_LONG).show();
//            return;
//        }

        //if the email and password are not empty
        //displaying a progress dialog

//        progressDialog.setMessage("Registering Please Wait...");
//        progressDialog.show();

        //creating a new user
        mAuth.createUserWithEmailAndPassword(mail, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        Context context = getApplicationContext();
                        CharSequence text = "Hello toast!";
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                        //checking if success
//                        if(task.isSuccessful()){
//                            //display some message here
//                            Toast.makeText(register.this,"Successfully registered",Toast.LENGTH_LONG).show();
//                        }else{
//                            //display some message here
//                            Toast.makeText(register.this,"Registration Error",Toast.LENGTH_LONG).show();

//                        progressDialog.dismiss();
                    }
                });

    }


    @Override
    public void onClick(View view) {

        if(view==save){

//            String mail = editMail.getText().toString();
//            String pass = editPass.getText().toString();
//            Context context = getApplicationContext();
//            CharSequence text = "Hello toast!";
//            int duration = Toast.LENGTH_SHORT;

//            Toast toast = Toast.makeText(context, text, duration);
//            toast.show();
            registerUser();


        }



    }
}
