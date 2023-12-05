package com.adventofcode.day01;

import static com.adventofcode.junit.util.AdventOfCodeAssertion.assertAdventOfCode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;

import com.adventofcode.junit.extension.AdventOfCodeDailySource;

class Day01Test {

	@ParameterizedTest
	@AdventOfCodeDailySource
	void testPart1(Path file) throws IOException {
		assertAdventOfCode(file, 142, combineDigits(Files.lines(file)));
	}

	@ParameterizedTest
	@AdventOfCodeDailySource
	void testPart2(Path file) throws IOException {
		assertAdventOfCode(file, 281, prepareForPart2(file));
	}

	private int prepareForPart2(Path file) throws IOException {
		// tag::replace[]
		return combineDigits(Files.lines(file).map(line -> line.replace("one", "o1e"))
				.map(line -> line.replace("two", "t2o")).map(line -> line.replace("three", "t3e"))
				.map(line -> line.replace("four", "f4r")).map(line -> line.replace("five", "f5e"))
				.map(line -> line.replace("six", "s6x")).map(line -> line.replace("seven", "s7n"))
				.map(line -> line.replace("eight", "e8t")).map(line -> line.replace("nine", "n9e")));
		// end::replace[]
	}

	private int combineDigits(Stream<String> file) throws IOException {
		return file.map(line -> line.replaceAll("[a-z]", "")).map(line -> line.length() > 1 ? line : line + line)
				.mapToInt(line -> Integer
						.parseInt(line.length() <= 2 ? line : line.substring(0, 1) + line.substring(line.length() - 1)))
				.sum();
	}

}
