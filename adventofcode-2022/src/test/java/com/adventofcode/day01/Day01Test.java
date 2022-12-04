package com.adventofcode.day01;

import static com.adventofcode.junit.util.AdventOfCodeAssertion.assertAdventOfCode;
import static java.util.stream.Collectors.groupingBy;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import org.junit.jupiter.params.ParameterizedTest;

import com.adventofcode.junit.extension.AdventOfCodeDailySource;

class Day01Test {

	@ParameterizedTest
	@AdventOfCodeDailySource
	void testPart1(Path filename) throws IOException {
		assertAdventOfCode(filename, 24000, sumupEachElvesCalories(filename).max().getAsInt());
	}

	@ParameterizedTest
	@AdventOfCodeDailySource
	void testPart2(Path filename) throws Exception {
		var maxCalsFromThreeElves = sumupEachElvesCalories(filename).boxed().sorted(Comparator.reverseOrder()).limit(3)
				.reduce(Integer::sum).get();

		assertAdventOfCode(filename, 45000, maxCalsFromThreeElves);
	}

	private IntStream sumupEachElvesCalories(Path filename) throws IOException {
		var group = new AtomicInteger();
		//@formatter:off
		// first load the file as a stream of strings
		return Files.lines(filename)
				// this is the tricky part: We need to split the stream by blank lines.
				// To use the collect function we need to have an external state for the key,
				// we are using, as we can not use an stream internal value, which is normally
				// used.
				.collect(
						// if the entry is blank we get a new key, else we use the current one
						groupingBy(x -> x.isBlank() ? group.getAndIncrement() : group.get()))
				// get the list of values, as we do not need the keys from the map
				// each List<String> represents an elve
				.values()
				.stream()
				// now we want an intstream, so we can sumup the calories
				.mapToInt(list -> list
									.stream()
									// only use entries, which are not blank
									.filter(s -> !s.isBlank())
									// just parse the string to an int
									.mapToInt(Integer::parseInt)
									// sum the calories of one elve
									.sum());
		//@formatter:on
	}

}
