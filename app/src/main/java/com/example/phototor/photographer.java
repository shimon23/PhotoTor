package com.example.phototor;
import java.util.HashMap;
import java.util.Map;
import java.util.*;


import java.util.Vector;

public class photographer extends User {

    public int lis;
    public final String userType = "photographer";
    //public Vector<String> EventType;
    public HashMap<String, String> EventAndPrice = new HashMap<String, String>();
    public String WebPage=null;

    public photographer(String firstName, String lastName, String email, String phoneNumber, String city, int lis){

        super(firstName,lastName,email,phoneNumber,city);
        this.lis = lis;

    }
    public void setEventAndPrice(String Event ,String Price){
        this.EventAndPrice.put(Event,Price);
    }

    public void setWebPage(String URL){
        this.WebPage=URL;
    }
    public void ChangePrice(String Event , String NewPrice){
        this.EventAndPrice.put(Event,NewPrice);
    }

}
