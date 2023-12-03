package com.adventofcode.day03;

import static com.adventofcode.junit.util.AdventOfCodeAssertion.assertAdventOfCode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;

import org.junit.jupiter.params.ParameterizedTest;

import com.adventofcode.junit.extension.AdventOfCodeDailySource;

class Day03Test {

	@ParameterizedTest
	@AdventOfCodeDailySource
	void testPart1(Path file) throws IOException {
		List<Integer> result = processFile(file, Day03Test::checkSkipPart1);
		assertAdventOfCode(file, 4361, result.stream().mapToInt(i -> i).sum());
	}

	private List<Integer> processFile(Path file, BiFunction<char[], Integer, Boolean> skip) throws IOException {
		List<Integer> result = new ArrayList<Integer>();
		List<String> readAllLines = Files.readAllLines(file);
		for (int i = 1; i < readAllLines.size() - 1; i++) {
			char[] line = readAllLines.get(i).toCharArray();
			for (int j = 1; j < line.length - 1; j++) {
				if (skip.apply(line, j)) {
					continue;
				}
				result.addAll(checkDigitsAround(j, i, readAllLines));
			}
		}
		return result;
	}

	private static boolean checkSkipPart1(char[] line, int j) {
		return line[j] == '.' || Character.isDigit(line[j]);
	}

	private static boolean checkSkipPart2(char[] line, int j) {
		return line[j] != '*';
	}

	@ParameterizedTest
	@AdventOfCodeDailySource
	void testPart2(Path file) throws IOException {
		List<String> readAllLines = Files.readAllLines(file);
		List<Integer> result = new ArrayList<Integer>();

		for (int i = 1; i < readAllLines.size() - 1; i++) {
			char[] line = readAllLines.get(i).toCharArray();
			for (int j = 1; j < line.length - 1; j++) {
				if (line[j] != '*') {
					continue;
				}
				result.add(checkGearAround(j, i, readAllLines));
			}
		}
		assertAdventOfCode(file, 467835, result.stream().mapToInt(i -> i).sum());
	}

	private Collection<? extends Integer> checkDigitsAround(int j, int i, List<String> readAllLines) {
		Set<Integer> result = backTrackForNumbers(j, i, readAllLines);

		return result;
	}

	private int checkGearAround(int j, int i, List<String> readAllLines) {
		Set<Integer> result = backTrackForNumbers(j, i, readAllLines);

		if (result.size() > 1) {
			return result.stream().reduce((n, m) -> n * m).orElse(0);
		}

		return 0;
	}

	private Set<Integer> backTrackForNumbers(int j, int i, List<String> readAllLines) {
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

	private Integer findInt(int j2, String string) {
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
