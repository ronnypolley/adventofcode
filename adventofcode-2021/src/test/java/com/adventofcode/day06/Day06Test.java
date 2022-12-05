package com.adventofcode.day06;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;

import com.adventofcode.junit.extension.AdventOfCodeDailySource;
import com.adventofcode.junit.util.AdventOfCodeAssertion;

class Day06Test {

	@ParameterizedTest
	@AdventOfCodeDailySource
	void testPart1(Path file) throws IOException {
		var input = Files.lines(file).map(string -> string.split(",")).flatMap(Arrays::stream).map(Integer::parseInt)
				.toList();

		for (var i = 0; i < 80; i++) {
			input = input.stream().flatMap(fish -> fish == 0 ? Stream.of(6, 8) : Stream.of(fish - 1)).toList();
		}
		AdventOfCodeAssertion.assertAdventOfCode(file, 5934, input.size());
	}

	@ParameterizedTest
	@AdventOfCodeDailySource
	void testPart2(Path file) throws IOException {
		var birthRate = new long[9];
		Files.lines(file).map(string -> string.split(",")).flatMap(Arrays::stream).map(Integer::parseInt)
				.forEach(i -> birthRate[i]++);

		for (var i = 0; i < 256; i++) {
			var oldZeros = birthRate[0];
			for (var j = 1; j < birthRate.length; j++) {
				birthRate[j - 1] = birthRate[j];
			}
			birthRate[6] += oldZeros;
			birthRate[8] = oldZeros;
		}

		AdventOfCodeAssertion.assertAdventOfCode(file, 26984457539L, Arrays.stream(birthRate).sum());
	}

}
