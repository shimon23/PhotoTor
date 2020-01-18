package com.example.phototor;

import java.util.HashMap;
import java.util.Map;



public class Order {


    int orderID;
    String photographerID;
    String clientID;
    String date;
    String time;
    int price;
    String status;
    String eventType;
    String location;
    String notes;


    public Order(int OrderID,String photographerID, String clientID, String date, String time, String eventType, String location, String notes) {


        this.status = "מחכה להצעת מחיר";
        this.orderID = OrderID;
        this.photographerID = photographerID;
        this.clientID = clientID;
        this.date = date;
        this.time = time;
        this.eventType = eventType;
        this.location = location;
        this.notes = notes;
        this.price = 0;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("orderID", orderID);
        result.put("status", status);
        result.put("price",price);


        result.put("photographerID", photographerID);
        result.put("clientID", clientID);
        result.put("date", date);
        result.put("time", time);
        result.put("eventType", eventType);
        result.put("location", location);
        result.put("notes",notes);

        return result;
    }


    public Order() {

    }

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getPhotographerID() {
        return photographerID;
    }

    public void setPhotographerID(String photographerID) {
        this.photographerID = photographerID;
    }
}
