package com.adventofcode.day01;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class Day01Test {

	@ParameterizedTest
	@ValueSource(strings = { "day01input_test", "day01input" })
	void testPart1(String filename) throws IOException {
		var group = new AtomicInteger();
		var maxCal = Files.lines(Paths.get("src/test/resources/" + filename))
				.collect(Collectors.groupingBy(x -> x.isBlank() ? group.getAndIncrement() : group.get())).values()
				.stream().mapToInt(list -> list.stream().filter(s -> !s.isBlank()).mapToInt(Integer::parseInt).sum())
				.max().getAsInt();

		if (filename.endsWith("test")) {
			assertEquals(24000, maxCal);
		}

		System.out.println(maxCal);
	}

	@ParameterizedTest
	@ValueSource(strings = { "day01input_test", "day01input" })
	void testPart2(String filename) throws Exception {
		var group = new AtomicInteger();
		var maxCalsFromThreeElves = Files.lines(Paths.get("src/test/resources/" + filename))
				.collect(Collectors.groupingBy(x -> x.isBlank() ? group.getAndIncrement() : group.get())).values()
				.stream().mapToInt(list -> list.stream().filter(s -> !s.isBlank()).mapToInt(Integer::parseInt).sum())
				.boxed().sorted(Comparator.reverseOrder()).limit(3).reduce(Integer::sum).get();

		if (filename.endsWith("test")) {
			assertEquals(45000, maxCalsFromThreeElves);
		}
		System.out.println(maxCalsFromThreeElves);
	}

}
