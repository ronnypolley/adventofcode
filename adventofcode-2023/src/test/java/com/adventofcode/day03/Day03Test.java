package com.adventofcode.day03;

import static com.adventofcode.junit.util.AdventOfCodeAssertion.assertAdventOfCode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;

import org.junit.jupiter.params.ParameterizedTest;

import com.adventofcode.junit.extension.AdventOfCodeDailySource;

class Day03Test {

	@ParameterizedTest
	@AdventOfCodeDailySource
	void testPart1(Path file) throws IOException {
		List<Integer> result = processFile(file, Part1Helper::checkSkipPart1, Part1Helper::checkDigitsAround);
		assertAdventOfCode(file, 4361, result.stream().mapToInt(i -> i).sum());
	}

	@ParameterizedTest
	@AdventOfCodeDailySource
	void testPart2(Path file) throws IOException {
		List<Integer> result = processFile(file, Part2Helper::checkSkipPart2, Part2Helper::checkGearAround);
		assertAdventOfCode(file, 467835, result.stream().mapToInt(i -> i).sum());
	}

	private List<Integer> processFile(Path file, BiFunction<char[], Integer, Boolean> skip,
			CheckDigitsAround<Integer, Integer, List<String>, Collection<? extends Integer>> findNumberFunction)
			throws IOException {
		List<Integer> result = new ArrayList<Integer>();
		List<String> readAllLines = Files.readAllLines(file);
		for (int i = 1; i < readAllLines.size() - 1; i++) {
			char[] line = readAllLines.get(i).toCharArray();
			for (int j = 1; j < line.length - 1; j++) {
				if (skip.apply(line, j)) {
					continue;
				}
				result.addAll(findNumberFunction.apply(j, i, readAllLines));
			}
		}
		return result;
	}

	@FunctionalInterface
	public interface CheckDigitsAround<T, U, V, R> {
		R apply(T j, T i, V listOfStrings);
	}

	private static class Part1Helper {

		private static boolean checkSkipPart1(char[] line, int j) {
			return line[j] == '.' || Character.isDigit(line[j]);
		}

		private static Collection<? extends Integer> checkDigitsAround(int j, int i, List<String> readAllLines) {
			Set<Integer> result = Day3Helper.backTrackForNumbers(j, i, readAllLines);

			return result;
		}
	}

	private static class Part2Helper {
		private static boolean checkSkipPart2(char[] line, int j) {
			return line[j] != '*';
		}

		private static Set<Integer> checkGearAround(int j, int i, List<String> readAllLines) {
			Set<Integer> result = Day3Helper.backTrackForNumbers(j, i, readAllLines);

			if (result.size() > 1) {
				return Collections.singleton(result.stream().reduce((n, m) -> n * m).orElse(0));
			}

			return Collections.singleton(0);
		}
	}

}
