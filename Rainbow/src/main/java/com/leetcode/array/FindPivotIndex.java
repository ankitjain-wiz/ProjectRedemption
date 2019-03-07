package com.leetcode.array;

public class FindPivotIndex {

	public static void main(String[] args) {
		int[] arrayOne = { 1, 2, 3, 4, 1, 5 };
		int[] arrayTwo = { 1, 2 };
		int[] arrayThree = { 5 };
		int[] arrayFour = { -1, -1, -1, 1, 1, 1 };

		FindPivotIndex object = new FindPivotIndex();
		System.out.println(object.pivotIndex(arrayOne));
		System.out.println(object.pivotIndex(arrayTwo));
		System.out.println(object.pivotIndex(arrayThree));
		System.out.println(object.pivotIndex(arrayFour));

	}

	public int pivotIndex(int[] nums) {

		int pivotIndex = 0;
		int sumBefore = 0;
		int sumAfter = 0;
		int temp = 0;

		if (nums.length < 2) {
			return -1;
		}
		while (pivotIndex < nums.length) {
			sumBefore = 0;
			sumAfter = 0;
			for (int i = 0; i < nums.length; i++) {
				if (i < pivotIndex) {
					sumBefore = sumBefore + nums[i];
				} else if (i > pivotIndex) {
					sumAfter = sumAfter + nums[i];
				}
			}
			if (sumAfter != sumBefore) {
				temp++;
				pivotIndex = temp;
			} else {
				return pivotIndex;
			}

		}

		return -1;
	}

	public int pivotIndexCopied(int[] nums) {
		if (nums.length < 3)
			return -1;
		int sum = 0;
		for (int n : nums)
			sum += n;
		int tempSum = 0;
		for (int i = 0; i < nums.length; i++) {
			if (tempSum * 2 == sum - nums[i])
				return i;
			else
				tempSum += nums[i];
		}
		return -1;
	}

}
