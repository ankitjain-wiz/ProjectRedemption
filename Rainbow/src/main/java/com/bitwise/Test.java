package com.bitwise;
class Test { 
    public static void main(String args[])  { 
       int x = -7; 
       System.out.println(x>>1);   // divide by 2 to the power n     
       int y = 11; 
       System.out.println(y>>1);    
       
       
       int a = -7; 
       System.out.println(a<<1);   /// 2 power 1 = 2 so 7 * 2 =14 
       int b = 11; 
       System.out.println(b<<1);   // 2 power 1 = 2 so 11 * 2 =22 
       

       int c = -7; 
       System.out.println(c<<2);   /// 2 power 2 = 2 so 7 * 4 =28 
       int d = 11; 
       System.out.println(d<<3);   // 2 power 3 = 8 so 11 * 8 =88 
       

       int e = 1; 
       System.out.println(e<<1);   
       int f = 2; 
       System.out.println(f<<1); 
       
       
       int n=3;
       int m=1;
       System.out.println("=====================================================================");
       for(int i=0;i < (1<<n);i++) {
    	   m=m<<1;
    	   System.out.println(m);
    	   System.out.println(i);
    	   System.out.println("=====================================================================");
       }
       
    }     
} 