package com.adventofcode.day07;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.params.ParameterizedTest;

import com.adventofcode.junit.extension.AdventOfCodeDailySource;
import com.adventofcode.junit.util.AdventOfCodeAssertion;

class Day07Test {

	@ParameterizedTest
	@AdventOfCodeDailySource
	void testPart1(Path file) throws IOException {
		var list = Files.lines(file, StandardCharsets.UTF_8).map(Hand::new).sorted()
				.collect(Collectors.toCollection(ArrayList::new));

		AdventOfCodeAssertion.assertAdventOfCode(file, 6440L, sumUp(list));
	}

	@ParameterizedTest
	@AdventOfCodeDailySource
	void testPart2(Path file) throws IOException {
		var list = Files.lines(file, StandardCharsets.UTF_8).map(Hand2::new).sorted()
				.collect(Collectors.toCollection(ArrayList::new));

		AdventOfCodeAssertion.assertAdventOfCode(file, 5905L, sumUp(list));
	}

	private <T extends Hand> long sumUp(List<T> list) {
		var sum = 0L;
		// sum up the bid mulitplied with the index
		for (var i = 0; i < list.size(); i++) {
			sum += list.get(i).bid * (i + 1);
		}
		return sum;
	}

}
