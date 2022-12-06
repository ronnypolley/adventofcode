package com.adventofcode.day06;

import static com.adventofcode.junit.util.AdventOfCodeAssertion.assertAdventOfCode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.params.ParameterizedTest;

import com.adventofcode.junit.extension.AdventOfCodeDailySource;

class Day06Test {

	@ParameterizedTest
	@AdventOfCodeDailySource
	void testPart1(Path file) throws IOException {
		var input = Files.readAllLines(file).get(0).chars().boxed().toList();
		assertAdventOfCode(file, 7, getIndex(input, 4));
	}

	@ParameterizedTest
	@AdventOfCodeDailySource
	void testPart2(Path file) throws IOException {
		var input = Files.readAllLines(file).get(0).chars().boxed().toList();
		assertAdventOfCode(file, 19, getIndex(input, 14));
	}

	private int getIndex(List<Integer> input, int neededLength) {
		for (var i = 0; i < input.size() - neededLength; i++) {
			if (new HashSet<>(input.subList(i, i + neededLength)).size() == neededLength) {
				return i + neededLength;
			}
		}
		return 0;
	}

}
