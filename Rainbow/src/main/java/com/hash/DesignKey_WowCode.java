package com.hash;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DesignKey_WowCode {

	public static void main(String[] args) {
		String input[] = { "eat", "tea", "tan", "ate", "nat", "bat" };
		groupAnagrams(input);

	}

	public static List<List<String>> groupAnagrams(String[] strs) {

		List<List<String>> listReturn = new ArrayList<>();
		Map<String, List<String>> stringMap = new HashMap<>();
		List<String> mapKeyList = null;

		for (int i = 0; i < strs.length; i++) {
			String word = strs[i];
			char word_charArray[] = word.toCharArray();
			Arrays.sort(word_charArray);
			String designedKey = String.valueOf(word_charArray);
			if (!stringMap.containsKey(designedKey)) {
				mapKeyList = new ArrayList<>();
				mapKeyList.add(word);
				stringMap.put(designedKey, mapKeyList);
			} else {
				mapKeyList = stringMap.get(designedKey);
				mapKeyList.add(word);

			}

		}

		stringMap.forEach((k, v) -> {
			listReturn.add(v);
		});

		return listReturn;

	}

	//ONE DAY WE WILL THINK LIKE THIS --> CREATIVITY AT ITS BEST
	// EXCELLENT SOLUTION
	public static boolean isValidSudoku(char[][] board) {

		Set<String> rowSet = new HashSet<>();
		Set<String> columnSet = new HashSet<>();
		Set<String> blockSet = new HashSet<>();

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				// int block = (i / 3)*3 + (j / 3);
				if (board[i][j] != '.') {
					String rowKey = board[i][j] + "is at row" + i;
					String columnKey = board[i][j] + "is at column" + j;
					String blockKey = board[i][j] + "is at block" + i / 3 + "" + j / 3;
					boolean rowCheck = rowSet.add(rowKey);
					boolean columnCheck = columnSet.add(columnKey);
					boolean blockCheck = blockSet.add(blockKey);
					if (!(rowCheck && columnCheck && blockCheck)) {
						return false;
					}
				}

			}
		}

		return true;
	}

}
