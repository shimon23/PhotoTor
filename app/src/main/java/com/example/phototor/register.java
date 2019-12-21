package com.example.phototor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;
import android.content.Context;


import com.google.firebase.auth.FirebaseAuth;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;

public class register extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    Button save;
    EditText editPass;
    EditText editMail;
    EditText editFirstName;
    EditText editLastName;
    EditText editPhoneNum;
    EditText editCity;

    RadioButton photoRadio;
    RadioButton clientRadio;
    String userType = "photographer";

    FirebaseDBUser usersDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        save =(Button)findViewById(R.id.registerBtn);

        editMail = (EditText) findViewById(R.id.editMail);
        editPass = (EditText) findViewById(R.id.editPass);

        photoRadio = (RadioButton) findViewById(R.id.radioBtnPhoto);
        clientRadio = (RadioButton) findViewById(R.id.radioBtnClient);
        editFirstName = (EditText) findViewById(R.id.editFirstName);
        editLastName = (EditText) findViewById(R.id.editLastName);
        editPhoneNum = (EditText) findViewById(R.id.editPhoneNum);
        editCity = (EditText) findViewById(R.id.editCity);

        usersDB = new FirebaseDBUser();

    }

    private void registerUser(){

        //getting email and password from edit texts
        String mail = editMail.getText().toString();
        String pass = editPass.getText().toString();
        String fName = editFirstName.getText().toString();
        String lName = editLastName.getText().toString();
        String phone = editPhoneNum.getText().toString();
        String city = editCity.getText().toString();



        if(!mail.isEmpty() && !pass.isEmpty() && !fName.isEmpty() && !lName.isEmpty() && !phone.isEmpty() && !city.isEmpty()) {
            //creating a new user
            mAuth.createUserWithEmailAndPassword(mail, pass)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isComplete()){


                                if (userType == "client") {
                                    //
                                }
                                else {



                                    String id = mAuth.getCurrentUser().getUid();
                                    String fName = editFirstName.getText().toString();
                                    String lName = editLastName.getText().toString();
                                    String phone = editPhoneNum.getText().toString();
                                    String city = editCity.getText().toString();
                                    String mail = editMail.getText().toString();

                                    usersDB.addUserToDB(id, fName, lName, mail, phone, city, "photographer");

                                }
                            }

                            else{

                                Toast.makeText(getApplicationContext(), "התחברת!",
                                        Toast.LENGTH_SHORT).show();

                            }



                        }
                    });




        }

    }


    @Override
    public void onClick(View view) {

        if(view==save){

            registerUser();
            startActivity(new Intent(this, photographerMenu.class));
            finish();

        }



    }

    public void radioPhotoClicked(View view)
    {

        if (photoRadio.isChecked()){
            clientRadio.setChecked(false);
            userType = "photographer";
        }

    }

    public void radioClientClicked(View view)
    {
        if (clientRadio.isChecked()){
            photoRadio.setChecked(false);
            userType = "client";
        }

    }


}
