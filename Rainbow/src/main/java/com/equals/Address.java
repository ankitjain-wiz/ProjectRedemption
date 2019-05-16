package com.equals;
class Address { 
private int houseNo; 
private String streetName; 
private String city; 
private int pinCode; 
  
    public Address(int houseNo, String streetName,  
                         String city, int pinCode) 
    { 
        this.houseNo = houseNo; 
        this.streetName = streetName; 
        this.city = city; 
        this.pinCode = pinCode; 
    } 
  
    public String getAddress() 
    { 
        return houseNo + ", " + streetName + 
                   ", " + city + ", " + pinCode; 
    } 
} 