package com.adventofcode.day01;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import org.junit.jupiter.params.ParameterizedTest;

import com.adventofcode.junit.extension.AdventOfCodeDailySource;
import com.adventofcode.junit.util.AdventOfCodeAssertion;

class Day01Test {

	@ParameterizedTest
	@AdventOfCodeDailySource
	void testPart1(Path file) throws IOException {
		AdventOfCodeAssertion.assertAdventOfCode(file, -3,
				Files.lines(file).flatMap(s -> Arrays.stream(s.split(""))).mapToInt(c -> c.equals("(") ? 1 : -1).sum());
	}

	@ParameterizedTest
	@AdventOfCodeDailySource
	void testPart2(Path file) throws IOException {
		var lines = Files.readAllLines(file);

		var currentFloor = 0;
		for (var i = 0; i < lines.get(0).length(); i++) {
			currentFloor += lines.get(0).charAt(i) == 40 ? 1 : -1;
			if (currentFloor == -1) {
				AdventOfCodeAssertion.assertAdventOfCode(file, 1, i + 1);
				return;
			}
		}
		fail();
	}

}
