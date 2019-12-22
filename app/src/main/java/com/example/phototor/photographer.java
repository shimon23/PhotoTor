package com.example.phototor;
import java.util.HashMap;
import java.util.Map;
import java.util.*;


import java.util.Vector;

public class photographer extends User {

    public final String userType = "photographer";
    //public Vector<String> EventType;
    public HashMap<String, String> EventAndPrice = new HashMap<String, String>();
    public String WebPage=null;

    public photographer(){

    }

    public String getUserType() {
        return userType;
    }

    public HashMap<String, String> getEventAndPrice() {
        return EventAndPrice;
    }

    public void setEventAndPrice(HashMap<String, String> eventAndPrice) {
        EventAndPrice = eventAndPrice;
    }

    public String getWebPage() {
        return WebPage;
    }

    public photographer(String id, String firstName, String lastName, String email, String phoneNumber, String city){

        super(id,firstName,lastName,email,phoneNumber,city);

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

    @Override
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("userID", userID);
        result.put("firstName", firstName);
        result.put("lastName", lastName);
        result.put("email", email);
        result.put("phoneNumber", phoneNumber);
        result.put("city", city);
        result.put("userType",userType);
        result.put("EventAndPrice",EventAndPrice);
        result.put("WebPage",WebPage);

        return result;
    }

}
