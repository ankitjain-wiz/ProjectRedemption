package com.leetcode.strings;

import java.util.Arrays;

public class Longest_Common_Prefix {

	public static void main(String[] args) {
		String[] array = {  };
		longestCommonPrefix(array);

	}

	private static String longestCommonPrefix(String[] strs) {

		if (strs.length<1  || strs[0].length() < 1) {
			return "";
		}

		int k = 0;
		String tempprefix = strs[0].substring(0, 1);
		String finalprefix = "";

		while (!tempprefix.equalsIgnoreCase("")) {
			String temp = strs[0].substring(k, k + 1);
			for (int i = 0; i < strs.length; i++) {
				if (!(k + 1 <= strs[i].length() && temp.equalsIgnoreCase(strs[i].substring(k, k + 1)))) {
					tempprefix = "";
					break;
				}
			}
			if (!tempprefix.equalsIgnoreCase("")) {
				finalprefix = finalprefix + temp;
				if (k + 1 == strs[0].length()) {
					break;
				} else {
					k++;
				}

			}

		}

		return finalprefix;

	}

}
