package com.example.phototor;

public class photographer extends User {

    public int lis;
    public final String userType = "photographer";

    public photographer(String firstName, String lastName, String email, String phoneNumber, String city, int lis){

        super(firstName,lastName,email,phoneNumber,city);
        this.lis = lis;

    }

}
