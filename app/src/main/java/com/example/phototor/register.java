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
import android.app.ProgressDialog;


import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

public class register extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    Button save;
    Button next;
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
    public ProgressDialog progressDialog;
    DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        save = (Button) findViewById(R.id.registerBtn);
        next = (Button) findViewById(R.id.nextBtn);

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

    private void registerUser() {

        progressDialog = new ProgressDialog(register.this,
                R.style.AppTheme);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("מבצע רישום..");
        progressDialog.show();

        //getting email and password from edit texts
        String mail = editMail.getText().toString();
        String pass = editPass.getText().toString();
        String fName = editFirstName.getText().toString();
        String lName = editLastName.getText().toString();
        String phone = editPhoneNum.getText().toString();
        String city = editCity.getText().toString();


        if (!mail.isEmpty() && !pass.isEmpty() && !fName.isEmpty() && !lName.isEmpty() && !phone.isEmpty() && !city.isEmpty()) {
            //creating a new user
            mAuth.createUserWithEmailAndPassword(mail, pass)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isComplete()) {

                                String id = mAuth.getCurrentUser().getUid();
                                String fName = editFirstName.getText().toString();
                                String lName = editLastName.getText().toString();
                                String phone = editPhoneNum.getText().toString();
                                String city = editCity.getText().toString();
                                String mail = editMail.getText().toString();


                                if (userType == "client") {

                                    final Map<String, Object> dataMap = new HashMap<String, Object>();
                                    User client = new User(id, fName, lName, mail, phone, city);
                                    dataMap.put(id, client.toMap());
                                    dbRef.child("users").updateChildren(dataMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            progressDialog.dismiss();
                                            next.setClickable(true);
                                            next.setVisibility(View.VISIBLE);
                                        }
                                    });
                                    //
                                } else {

                                    final Map<String, Object> dataMap = new HashMap<String, Object>();
                                    photographer userPhoto = new photographer(id, fName, lName, mail, phone, city);
                                    dataMap.put(id, userPhoto.toMap());
                                    dbRef.child("users").updateChildren(dataMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            progressDialog.dismiss();
                                            next.setClickable(true);
                                            next.setVisibility(View.VISIBLE);
                                        }
                                    });

//                                    usersDB.addUserToDB(id, fName, lName, mail, phone, city, "photographer");
//                                    Toast.makeText(getApplicationContext(), "נרשמת בהצלחה!",
//                                            Toast.LENGTH_SHORT).show();

                                }
                            } else {

//                                Toast.makeText(getApplicationContext(), "התחברת!",
//                                        Toast.LENGTH_SHORT).show();

                            }


                        }
                    });


        }


    }


    @Override
    public void onClick(View view) {

        if (view == save) {

            registerUser();

        }

        if (view == next) {

            if (userType == "client") {
                startActivity(new Intent(this, clientMenu.class));
                finish();
            }
            else{
            startActivity(new Intent(this, photographerMenu.class));
            finish();
        }
        }


    }

    public void radioPhotoClicked(View view) {

        if (photoRadio.isChecked()) {
            clientRadio.setChecked(false);
            userType = "photographer";
        }

    }

    public void radioClientClicked(View view) {
        if (clientRadio.isChecked()) {
            photoRadio.setChecked(false);
            userType = "client";
        }

    }


}
