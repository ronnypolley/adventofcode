package com.adventofcode.day03;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

class Day3Helper {

	static Set<Integer> backTrackForNumbers(int j, int i, List<String> readAllLines) {
		Set<Integer> result = new HashSet<Integer>();

		for (int i2 = i - 1; i2 < i + 2; i2++) {
			for (int j2 = j - 1; j2 < j + 2; j2++) {
				if (Character.isDigit(readAllLines.get(i2).charAt(j2))) {
					result.add(findInt(j2, readAllLines.get(i2)));
				}
			}
		}
		return result;
	}

	static Integer findInt(int j2, String string) {
		int left = j2;
		int right = j2;
		while (left > 0 && Character.isDigit(string.charAt(left)) && Character.isDigit(string.charAt(left - 1))) {
			left--;
		}
		while (right < string.length() && Character.isDigit(string.charAt(right))) {
			right++;
		}
		return Integer.parseInt(string.substring(left, right));
	}
}