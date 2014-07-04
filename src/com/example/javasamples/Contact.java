package com.example.javasamples;
public class Contact {
     
    //private variables
    String timestamp;
    String latitude;
    String longitude;
    String Lastx;
    String Lasty;
    String Lastz;
    String degree;
    // Empty constructor
    public Contact(){
         
    }
    // constructor
    public Contact(String timestamp, String name, String longitude,String lastx,String lasty,String lastz,String degree){
        this.timestamp = timestamp;
        this.latitude = name;
        this.longitude = longitude;
        this.Lastx=lastx;
        this.Lasty=lasty;
        this.Lastz=lastz;
        this.degree=degree;
    }
     
    // constructor
    public Contact(String latitude, String longitude){
        this.latitude = latitude;
        this.longitude = longitude;
    }
    // getting ID
    public String getID(){
        return this.timestamp;
    }
     
    // setting id
    public void setID(String id){
        this.timestamp = id;
    }
     
    public String getLastx(){
        return this.Lastx;
    }
    
    public String getLasty(){
        return this.Lasty;
    }
    public String getLastz(){
        return this.Lastz;
    }
    public void setLastx(String lastx){
        this.Lastx = lastx;
    }
    public void setLasty(String lasty){
        this.Lasty = lasty;
    }
    public void setLastz(String lastz){
        this.Lastz = lastz;
    }
    public String getDegree(){
        return this.degree;
    }
    public void setDegree(String degree){
        this.degree = degree;
    }
    
    // getting name
    public String getName(){
        return this.latitude;
    }
     
    // setting name
    public void setName(String name){
        this.latitude = name;
    }
     
    // getting phone number
    public String getPhoneNumber(){
        return this.longitude;
    }
     
    // setting phone number
    public void setPhoneNumber(String phone_number){
        this.longitude = phone_number;
    }
}