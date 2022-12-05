package com.adventofcode.day07;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.OptionalInt;
import java.util.stream.IntStream;

import org.junit.jupiter.params.ParameterizedTest;

import com.adventofcode.junit.extension.AdventOfCodeDailySource;
import com.adventofcode.junit.util.AdventOfCodeAssertion;

class Day07Test {

	@ParameterizedTest
	@AdventOfCodeDailySource
	void testPart1(Path file) throws IOException {
		var input = Files.lines(file).flatMap(s -> Arrays.stream(s.split(","))).map(Integer::parseInt).toList();
		var max = input.stream().max(Integer::compare);
		var result = OptionalInt.empty();
		if (max.isPresent()) {
			result = IntStream.range(0, max.get()).map(i -> input.stream().mapToInt(c -> Math.abs(i - c)).sum()).min();
		}
		AdventOfCodeAssertion.assertAdventOfCode(file, 37, result.orElse(-1));
	}

	@ParameterizedTest
	@AdventOfCodeDailySource
	void testPart2(Path file) throws IOException {
		var input = Files.lines(file).flatMap(s -> Arrays.stream(s.split(","))).map(Integer::parseInt).toList();
		var max = input.stream().max(Integer::compare);
		var result = OptionalInt.empty();
		if (max.isPresent()) {
			result = IntStream.range(0, max.get())
					.map(i -> input.stream().mapToInt(c -> IntStream.rangeClosed(0, Math.abs(i - c)).sum()).sum())
					.min();
		}
		AdventOfCodeAssertion.assertAdventOfCode(file, 168, result.orElse(-1));
	}
}
