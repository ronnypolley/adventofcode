package com.adventofcode.day08;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections4.iterators.PermutationIterator;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;

import com.adventofcode.junit.extension.AdventOfCodeDailySource;
import com.adventofcode.junit.util.AdventOfCodeAssertion;

class Day08Test {

	@ParameterizedTest
	@AdventOfCodeDailySource
	void testPart1(Path file) throws IOException {
		List<Integer> lengths = Arrays.asList(2, 3, 4, 7);
		AdventOfCodeAssertion.assertAdventOfCode(file, 26, Files
				.lines(file).mapToInt(l -> (int) Arrays.stream(l.substring(l.lastIndexOf('|') + 1).split(" "))
						.map(String::trim).filter(s -> !s.isBlank()).filter(s -> lengths.contains(s.length())).count())
				.sum());
	}

	@ParameterizedTest
	@AdventOfCodeDailySource
	@Disabled("does not really work in my head right now")
	void testPart2(Path file) throws IOException {
		List<Integer> lengths = Arrays.asList(2, 3, 4, 7);

		System.out.println(getPerumatatedDigits(List.of("a", "b", "c", "d", "e", "f", "g")));

		//@formatter:off
		AdventOfCodeAssertion.assertAdventOfCode(file, 61229, Files
				.lines(file)
				.mapToInt(l -> (int) Arrays.stream(l.substring(l.lastIndexOf('|') + 1).split(" "))
						.map(String::trim).filter(s -> !s.isBlank()).filter(s -> lengths.contains(s.length())).count())
				.sum());
		//@formatter:on
	}

	// List<String> inputChars = List.of("a", "b", "c", "d", "e", "f", "g");
	List<List<String>> getPerumatatedDigits(List<String> input) {
		List<List<String>> result = new ArrayList<>();
		new PermutationIterator<>(input).forEachRemaining(per -> result.add(per));
		return result;
	}

}
