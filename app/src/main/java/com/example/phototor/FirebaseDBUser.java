package com.example.phototor;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.android.gms.tasks.OnSuccessListener;


import com.google.firebase.database.DatabaseReference;

public class FirebaseDBUser extends FirbaseModel {

    public ProgressDialog progressDialog;


    public void addUserToDB(String id, String firstName, String lastName, String email, String phoneNumber, String city, String userType) {

        if (userType == "client") {
            return;
        } else {
            writeNewPhotographer(id, firstName, lastName, email, phoneNumber, city);

        }

    }

    public void writeNewPhotographer(String id, String firstName, String lastName, String email, String phoneNumber, String city) {

        final Map<String, Object> dataMap = new HashMap<String, Object>();
        photographer userPhoto = new photographer(id, firstName, lastName, email, phoneNumber, city);
        dataMap.put("user details", userPhoto.toMap());
        dbRef.child("users").child("photographers").child(id).updateChildren(dataMap);


    }

    public DatabaseReference getUserFromDB(String userId) {
        return dbRef.getRef().child("photographers").child(userId);
    }

}
