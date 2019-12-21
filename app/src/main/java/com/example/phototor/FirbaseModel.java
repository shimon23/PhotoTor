package com.example.phototor;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirbaseModel {

    protected DatabaseReference dbRef;

    public  FirbaseModel(){
        dbRef = FirebaseDatabase.getInstance().getReference();
    }



}
