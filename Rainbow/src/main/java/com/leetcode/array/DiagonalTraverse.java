package com.leetcode.array;

import java.util.Scanner;

public class DiagonalTraverse {

	public static void main(String[] args) {
		int[][] num = new int[3][4];
		Scanner sc = new Scanner(System.in);
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 4; j++) {
				num[i][j] = sc.nextInt();
			}
		}
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 4; j++) {
				System.out.println(num[i][j]);
			}
		}

		findDiagonalOrder(num);

	}

	static int[] findDiagonalOrder(int[][] matrix) {
		int[] answer = new int[20];
		int k = 0, i = 0, j = 0, c = 0;
		while (k < 12) {
			if (c % 2 == 0) {
				i = c;
				j = 0;
				while (i >= 0&&j<4) {
					answer[k] = matrix[i][j];
					k++;
					i--;
					j++;
					c=j;
					if(c>4) c=3;
				}

			} else {

				i = 0;
				j = c;
				while (j >= 0&&i<3) {
					answer[k] = matrix[i][j];
					k++;
					j--;
					i++;
					c=i;
					if(c>3) c=2;
				}

			}
			
		}

		return answer;
	}

}
