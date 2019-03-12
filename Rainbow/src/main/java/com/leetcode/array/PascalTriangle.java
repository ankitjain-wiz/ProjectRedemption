package com.leetcode.array;

import java.util.ArrayList;
import java.util.List;

public class PascalTriangle {

	public static void main(String[] args) {

		generate(5);
		/*
		 * String s="ankit"; String s2=new String("ankit"); System.out.println(s==s2);
		 */

	}

	static List<List<Integer>> generate(int numRows) {

		List<List<Integer>> bigList = new ArrayList<>();
		if (numRows >= 1) {
			int array[][] = new int[numRows][numRows];
			int rowLength = array.length;
			int columnLength = array[0].length;
			for (int i = 0; i < rowLength; i++) {
				for (int j = 0; j < columnLength; j++) {
					if (j == 0) {
						array[i][j] = 1;
					} else {
						array[i][j] = 0;
					}

				}
			}

			for (int i = 0; i < rowLength; i++) {
				int j = 0;
				while (j <= i) {
					if (i != 0 && j != 0) {
						array[i][j] = array[i - 1][j - 1] + array[i - 1][j];
					}
					j++;

				}
			}

			for (int i = 0; i < rowLength; i++) {
				List<Integer> row = new ArrayList<>();
				for (int j = 0; j < columnLength; j++) {
					if (array[i][j] != 0) {
						row.add(array[i][j]);
					}
				}
				bigList.add(row);
			}
		}

		return bigList;

	}

}
