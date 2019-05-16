package com.equals;

public class One {

	public static void main(String[] args) {
		One a=new One();
		One b=new One();
		System.out.println(a.equals(b));
		System.out.println(a==b);
		
		 String s1 = new String("HELLO"); 
	     String s2 = new String("HELLO"); 
	     System.out.println(s1 == s2); 
	     System.out.println(s1.equals(s2));
	     
	     int x=9;
	     int vv=0;
	     //== works with both primitive and object but equls wqorks with primitive
	     
	
	     
	     
	     //very important note
	     //if you have a default implementation of equls method then it will call but if you donot have then object class equls method will call
	     //== compares the references and if you do not have default implementation of equls method whtn it also compares references
	     // eqals in String is overridem
	     
	     //But there is one relation between this two is default implementation of equals() method work like == means it will check the memory reference of the object if they point to the same location then two objects are equals and it is defined in Object class.


	}

}
