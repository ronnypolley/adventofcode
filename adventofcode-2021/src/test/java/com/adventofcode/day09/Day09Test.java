package com.adventofcode.day09;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.IntStream;

import org.junit.jupiter.params.ParameterizedTest;

import com.adventofcode.junit.extension.AdventOfCodeDailySource;
import com.adventofcode.junit.util.AdventOfCodeAssertion;

class Day09Test {

	@ParameterizedTest
	@AdventOfCodeDailySource
	void testPart1(Path file) throws IOException {
		var lines = Files.readAllLines(file);

		var matrix = new int[lines.size() + 2][lines.get(0).length() + 2];
		IntStream.rangeClosed(0, lines.size() + 1)
				.forEach(i -> IntStream.rangeClosed(0, lines.get(0).length() + 1).forEach(j -> matrix[i][j] = 9));
		IntStream.range(0, lines.size()).forEach(i -> IntStream.range(0, lines.get(0).length())
				.forEach(j -> matrix[i + 1][j + 1] = Integer.parseInt(lines.get(i).substring(j, j + 1))));

		var sum = IntStream.range(1, lines.size() + 1).flatMap(i -> IntStream.range(1, lines.get(0).length() + 1)
				.map(j -> (matrix[i][j] < matrix[i + 1][j] && matrix[i][j] < matrix[i][j + 1]
						&& matrix[i][j] < matrix[i - 1][j] && matrix[i][j] < matrix[i][j - 1] ? matrix[i][j] : -1)))
				.map(i -> i + 1).sum();

		AdventOfCodeAssertion.assertAdventOfCode(file, 15, sum);
	}

	@ParameterizedTest
	@AdventOfCodeDailySource
	void testPart2(Path file) throws IOException {
		var lines = Files.readAllLines(file);

		var matrix = new int[lines.size() + 2][lines.get(0).length() + 2];
		IntStream.rangeClosed(0, lines.size() + 1)
				.forEach(i -> IntStream.rangeClosed(0, lines.get(0).length() + 1).forEach(j -> matrix[i][j] = 9));
		IntStream.range(0, lines.size()).forEach(i -> IntStream.range(0, lines.get(0).length())
				.forEach(j -> matrix[i + 1][j + 1] = Integer.parseInt(lines.get(i).substring(j, j + 1))));

		var sum = IntStream.range(1, lines.size() + 1)
				.flatMap(i -> IntStream.range(1, lines.get(0).length() + 1)
						.map(j -> (matrix[i][j] < matrix[i + 1][j] && matrix[i][j] < matrix[i][j + 1]
								&& matrix[i][j] < matrix[i - 1][j] && matrix[i][j] < matrix[i][j - 1]
										? computeBasin(copyOfMatrix(matrix), i, j)
										: -1)))
				.boxed().sorted(Comparator.reverseOrder()).limit(3).reduce((i, j) -> i * j);

		AdventOfCodeAssertion.assertAdventOfCode(file, 1134, sum.orElse(0));
	}

	int[][] copyOfMatrix(int[][] matrix) {
		var result = new int[matrix.length][matrix[0].length];
		for (var i = 0; i < result.length; i++) {
			result[i] = Arrays.copyOf(matrix[i], matrix[i].length);
		}
		return result;
	}

	int computeBasin(int[][] matrix, int i, int j) {
		if (matrix[i][j] == 9) {
			return 0;
		}
		matrix[i][j] = 9;
		return computeBasin(matrix, i, j - 1) + computeBasin(matrix, i, j + 1) + computeBasin(matrix, i - 1, j)
				+ computeBasin(matrix, i + 1, j) + 1;
	}

}
