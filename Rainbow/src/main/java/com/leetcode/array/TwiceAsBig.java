package com.leetcode.array;

import java.util.Arrays;

public class TwiceAsBig {

	public static void main(String[] args) {

		int[] num = { 6, 1, 2, 0, 1, 3 };

		System.out.println(dominantIndex(num));

	}

	static int dominantIndex(int[] nums) {

		int compare[] = new int[nums.length];
		for (int i = 0; i < nums.length; i++) {
			compare[i] = nums[i];
		}

		Arrays.sort(nums);
		int position = nums.length - 1;
		int max = nums[position];
		boolean flag = true;
		for (int i = 0; i < nums.length - 1; i++) {
			if (2 * nums[i] <= max) {
				flag = true;
			} else {
				flag = false;
			}
			if (flag == false) {
				break;
			}
		}

		if (flag == true) {
			for (int i = 0; i < compare.length; i++) {
				if (compare[i] == max) {
					return i;
				}
			}

		}
		return -1;

	}

}
