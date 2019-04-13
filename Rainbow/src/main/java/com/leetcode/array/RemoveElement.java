package com.leetcode.array;

import java.util.Arrays;

public class RemoveElement {

	public static void main(String[] args) {

		//GOOD QUESTION 
		int num[] = {2};
		System.out.println("output="+output(num,3));

	}

	static int output(int nums[],int val) {
        
		Arrays.sort(nums);
		int k=0;
		int output=0;
		for(int i=0;i<nums.length;i++) {
			if(nums[i]==val) {
				k++;
			}else {
				output++;
			}
		}
		
		int t=output;
		if(t==nums.length) {
			return t;
		}
		int temp;
		//swap with last
		for(int i=0;i<output;i++) {
			if(nums[i]==val&&k>0) {
				temp=nums[i];
				nums[i]=nums[i+k];
				nums[i+k]=temp;
			}
		}
		
		for(int i=0;i<nums.length;i++) {
			System.out.println(nums[i]);
		}

		return output;
	
	}

}
