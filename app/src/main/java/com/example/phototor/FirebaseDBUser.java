package com.example.phototor;

import java.util.HashMap;
import java.util.Map;

import com.google.firebase.database.DatabaseReference;

public class FirebaseDBUser extends FirbaseModel {

    public void addUserToDB(String id,String firstName, String lastName, String email, String phoneNumber, String city,String userType){

        if(userType=="client"){
            return;
        }
        else{
            writeNewPhotographer(id, firstName, lastName, email, phoneNumber, city);

        }

    }

    public void writeNewPhotographer(String id,String firstName, String lastName, String email, String phoneNumber, String city){

        final Map<String, Object> dataMap = new HashMap<String, Object>();
        photographer userPhoto = new photographer(id,firstName,lastName,email,phoneNumber,city);
        dataMap.put("user details", userPhoto.toMap());
        dbRef.child("users").child("photographers").child(id).updateChildren(dataMap);

    }

    public DatabaseReference getUserFromDB(String userId){
        return dbRef.getRef().child("photographers").child(userId);
    }

}
