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

	/**
	 * @param file               the input file
	 * @param skip               function to be used to determine chars to skip
	 * @param findNumberFunction function to find the numbers based on part1/part2
	 * @return the list of all relevant numbers
	 * @throws IOException
	 */
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
		// this interfaces is used for the check*Around methods to be referenced.
		R apply(T j, T i, V listOfStrings);
	}

	private static class Part1Helper {

		private static boolean checkSkipPart1(char[] line, int j) {
			// skip everything that is a . or a digit, we only want to start with the symbol
			return line[j] == '.' || Character.isDigit(line[j]);
		}

		private static Collection<? extends Integer> checkDigitsAround(int j, int i, List<String> readAllLines) {
			// just backtrack to find the numbers
			return Day3Helper.backTrackForNumbers(j, i, readAllLines);
		}
	}

	private static class Part2Helper {
		private static boolean checkSkipPart2(char[] line, int j) {
			// skip everything that is not a * as this is gear
			return line[j] != '*';
		}

		private static Set<Integer> checkGearAround(int j, int i, List<String> readAllLines) {
			Set<Integer> result = Day3Helper.backTrackForNumbers(j, i, readAllLines);

			// only count the gears with 2 or more numbers around it.
			if (result.size() > 1) {
				return Collections.singleton(result.stream().reduce((n, m) -> n * m).orElse(0));
			}

			return Collections.singleton(0);
		}
	}

}
