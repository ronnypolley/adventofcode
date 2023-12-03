package com.adventofcode.day04;

import static com.adventofcode.junit.util.AdventOfCodeAssertion.assertAdventOfCode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.params.ParameterizedTest;

import com.adventofcode.junit.extension.AdventOfCodeDailySource;

class Day04Test {

	@ParameterizedTest
	@AdventOfCodeDailySource
	void testPart1(Path path) throws IOException {
		var overlaps = Files.lines(path).map(this::split).filter(
				element -> element.get(0).containsAll(element.get(1)) || element.get(1).containsAll(element.get(0)))
				.count();
		
		assertAdventOfCode(path, 2L, overlaps);
	}

	@ParameterizedTest
	@AdventOfCodeDailySource
	void testPart2(Path filename) throws IOException {

		var overlaps = Files.lines(filename).map(this::split)
				.filter(element -> !Collections.disjoint(element.get(0), element.get(1))).count();

		assertAdventOfCode(filename, 4L, overlaps);
	}

	private List<List<Integer>> split(String string1) {
		var split = string1.split(",");
		List<List<Integer>> result = new ArrayList<>();
		for (String element : split) {
			var split2 = element.split("-");
			result.add(
					IntStream.rangeClosed(Integer.parseInt(split2[0]), Integer.parseInt(split2[1])).boxed().toList());
		}
		return result;
	}

}
