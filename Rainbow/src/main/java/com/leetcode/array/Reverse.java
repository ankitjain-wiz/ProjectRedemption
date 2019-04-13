package com.leetcode.array;

public class Reverse {

	public static void main(String[] args) {
		char array[]= {'h','e','l','l','o'};
		String sarray="hello";
		System.out.println(array[0]);
		reverseCharArray(array);
		reverseString(sarray);
		

	}

	static void reverseString(String s) {

	}
	
	
	static void reverseCharArray(char[] s) {
		
		int i=0;
		int j=s.length-1;
		char temp;
		while(i<=j) {
			temp=s[i];
			s[i]=s[j];
			s[j]=temp;
			i++;
			j--;
			
		}
		
		System.out.println(s);

	}

}
