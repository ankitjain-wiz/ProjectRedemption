package com.leetcode.array;

import java.util.Arrays;

public class ArrayPartition {

	public static void main(String[] args) {
		
		int num[]= {10,2,3,40};
		System.out.println(output(num));
		

	}
	
	static int output(int num[]) {
		Arrays.sort(num);
		int output=0;
		int i=0;
		while(i<num.length) {
			output+=num[i];
			i=i+2;
			
		}
		
		
		return output;
	}

}
