package com.hash;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

class HashSetLeetCode {

	public static void main(String[] args) {
		int nums[] = { 1, 2, 5, 4, 5 };
		int nums2[] = { 1, 2, 2, 3, 3 };
		System.out.println(containsDuplicate(nums));
		System.out.println(singleNumber(nums2));
		intersection(nums, nums2);

	}

	public static boolean containsDuplicate(int[] nums) {

		Set<Integer> intHashSet = new HashSet<>();
		boolean duplicate;
		for (int i : nums) {
			duplicate = intHashSet.add(i);
			if (duplicate == false) {
				return true;
			}
		}

		return false;
	}

	public static int singleNumber(int[] nums) {
		int singleElement = 0;
		boolean duplicate;
		/* int secondArray[]=new int[nums.length]; */
		List<Integer> secondArray = new ArrayList<>();
		Set<Integer> intHashSet = new HashSet<>();
		for (int i : nums) {
			duplicate = intHashSet.add(i);
			if (duplicate == false) {
				secondArray.add(i);
			}
		}

		for (int i = 0; i < nums.length; i++) {
			boolean exist = false;
			for (int j = 0; j < secondArray.size(); j++) {
				if (secondArray.get(j) == nums[i]) {
					exist = true;
				}
			}
			if (exist == false) {
				return nums[i];
			}
		}

		return singleElement;

	}

	public static int[] intersection(int[] nums1, int[] nums2) {
		int len1 = nums1.length;
		int len2 = nums2.length;
		// Set seen = new HashSet();
		/// int min = (len1>len2)?len2:len1;
		// int max = (len1>len2)?len1:len2;

		Set<Integer> intHashSet = new HashSet<>();

		for (int i = 0; i < len1; i++) {
			for (int j = 0; j < len2; j++) {
				if (nums1[i] == nums2[j]) {
					intHashSet.add(nums1[i]);
				}
			}
		}

		Map<Integer, Integer> hashmap = new HashMap<>();
		Map<Integer, Integer> hashmapFinal = new HashMap<>();
		for (int i : nums1) {
			if (!hashmap.containsKey(i)) {
				hashmap.put(i, i);
			}
		}

		for (int j : nums2) {
			if (hashmap.containsKey(j)) {
				hashmapFinal.put(j, j);
			}
		}

		int arrReturnable[] = new int[hashmapFinal.size()];
		int i = 0;
		for (Integer key : hashmapFinal.values()) {
			arrReturnable[i] = key;
			i++;
		}

		return arrReturnable;

	}
}