package com.adventofcode.day04;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class Day04Test {

	@ParameterizedTest
	@ValueSource(strings = { "day04input_test", "day04input" })
	void testPart1(String filename) throws IOException {
		var overlaps = Files.lines(Paths.get("src/test/resources/" + filename)).map(this::split).filter(
				element -> element.get(0).containsAll(element.get(1)) || element.get(1).containsAll(element.get(0)))
				.count();

		if (filename.endsWith("test")) {
			assertEquals(2, overlaps);
		}
		System.out.println(overlaps);
	}

	@ParameterizedTest
	@ValueSource(strings = { "day04input_test", "day04input" })
	void testPart2(String filename) throws IOException {

		var overlaps = Files.lines(Paths.get("src/test/resources/" + filename)).map(this::split)
				.filter(element -> !Collections.disjoint(element.get(0), element.get(1))).count();

		if (filename.endsWith("test")) {
			assertEquals(4, overlaps);
		}
		System.out.println(overlaps);
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
