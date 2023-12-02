package com.adventofcode.day01;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;

import com.adventofcode.junit.extension.AdventOfCodeDailySource;
import static com.adventofcode.junit.util.AdventOfCodeAssertion.assertAdventOfCode;

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

	private Object prepareForPart2(Path file) throws IOException {
		// TODO Auto-generated method stub
		return combineDigits(Files.lines(file).map(line -> line.replaceAll("one", "o1e"))
				.map(line -> line.replaceAll("two", "t2o"))
				.map(line -> line.replaceAll("three", "t3e"))
				.map(line -> line.replaceAll("four", "f4r"))
				.map(line -> line.replaceAll("five", "f5e"))
				.map(line -> line.replaceAll("six", "s6x"))
				.map(line -> line.replaceAll("seven", "s7n"))
				.map(line -> line.replaceAll("eight", "e8t"))
				.map(line -> line.replaceAll("nine", "n9e")));
	}

	private Object combineDigits(Stream<String> file) throws IOException {
		return file.map(line -> line.replaceAll("[a-z]", "")).map(line -> line.length() > 1 ? line : line + line).mapToInt(line -> Integer.parseInt(line.length() <=2 ? line : line.substring(0, 1) + line.substring(line.length()-1))).sum();
	}

}
