package com.equals;

import java.util.HashMap;
import java.util.Map;

public class Test { 
    public static String getAddress(Map map, Employee emp) 
    { 
        Address adrs = (Address)map.get(emp); 
        return adrs.getAddress(); 
    } 
    public static void main(String[] args) 
    { 
  
        Employee emp1 = new Employee(110, "Sajid Ali Khan"); 
        Address adrs1 = new Address(304, "Marol Mahrisi",  
                                         "Mumbai", 400069); 
  
        Employee emp2 = new Employee(111, "Jaspreet Singh"); 
        Address adrs2 = new Address(203, "Seepz", "Mumbai",  
                                                 400093); 
  
        Map<Employee, Address> map = new HashMap<>(); 
        map.put(emp1, adrs1); 
        map.put(emp2, adrs2); 
        
        
        System.out.println(emp1.equals(null));
  
        
        System.out.println(Test.getAddress(map,emp1) ); 
        /*System.out.println(Test.getAddress(map, new Employee(110,  
                                              "Sajid Ali Khan"))); */
    } 
} 