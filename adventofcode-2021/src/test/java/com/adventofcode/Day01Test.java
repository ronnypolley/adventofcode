package com.adventofcode;

import static com.adventofcode.junit.util.AdventOfCodeAssertion.assertAdventOfCode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.params.ParameterizedTest;

import com.adventofcode.junit.extension.AdventOfCodeDailySource;

class Day01Test {

	@ParameterizedTest
	@AdventOfCodeDailySource
	void testPart1(Path file) throws IOException {
		Files.lines(file).mapToInt(Integer::parseInt).reduce(this::findIncreasing);
		assertAdventOfCode(file, 7, increases);
	}

	int increases = 0;

	private int findIncreasing(int pre_measure, int current_measure) {
		increases += pre_measure < current_measure ? 1 : 0;
		return current_measure;
	}

	@ParameterizedTest
	@AdventOfCodeDailySource
	void testPart2(Path file) throws Exception {
		List<Integer> list = Files.lines(file).mapToInt(Integer::parseInt).boxed().collect(Collectors.toList());
		for (var i = 0; i < list.size() - 2; i++) {
			increases += list.stream().skip(i).limit(3).reduce(0, Integer::sum) < list.stream().skip(i + 1).limit(3)
					.reduce(0, Integer::sum) ? 1 : 0;
		}
		assertAdventOfCode(file, 5, increases);
	}
}