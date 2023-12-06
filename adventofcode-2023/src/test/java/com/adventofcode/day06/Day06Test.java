package com.adventofcode.day06;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.LongStream;

import org.junit.jupiter.params.ParameterizedTest;

import com.adventofcode.junit.extension.AdventOfCodeDailySource;
import com.adventofcode.junit.util.AdventOfCodeAssertion;

class Day06Test {

	@ParameterizedTest
	@AdventOfCodeDailySource
	void testPart1(Path file) throws IOException {
		var list = exctractActualRaceValuesFromInput(file, this::part1StringMapping);
		// mapping is not quite nice. Could be made better, with a better parsing of the
		// input
		var races = new ArrayList<Race>();
		for (var i = 0; i < list.get(0).size(); i++) {
			races.add(new Race(list.get(0).get(i), list.get(1).get(i)));
		}

		// just compute the distance by multiple the rest time with the difference of
		// max time and the "index"
		AdventOfCodeAssertion
				.assertAdventOfCode(file, 288L,
						races.stream()
								.mapToLong(value -> LongStream.rangeClosed(0, value.time)
										.map(t -> ((value.time - t) * t)).filter(d -> value.distance < d).count())
								.reduce((i, j) -> i * j).getAsLong());
	}

	private String[] part1StringMapping(String l) {
		return l.replaceFirst(".*:", "").strip().split(" ");
	}

	@ParameterizedTest
	@AdventOfCodeDailySource
	void testPart2(Path file) throws IOException {
		var list = exctractActualRaceValuesFromInput(file, this::part2StringMapping);
		var race = new Race(list.get(0).get(0), list.get(1).get(0));

		AdventOfCodeAssertion.assertAdventOfCode(file, 71503L, LongStream.rangeClosed(0, race.time)
				.map(t -> ((race.time - t) * t)).filter(d -> race.distance < d).count());
	}

	private String[] part2StringMapping(String l) {
		return new String[] { l.replaceFirst(".*:", "").strip().replaceAll("\\s*", "") };
	}

	private List<List<Long>> exctractActualRaceValuesFromInput(Path file, Function<String, String[]> function)
			throws IOException {
		return Files.lines(file).map(l -> Arrays.asList(function.apply(l)))
				.map(l -> l.stream().filter(s -> !s.isBlank()).mapToLong(Long::parseLong).boxed().toList()).toList();
	}

	record Race(Long time, Long distance) {
	}

}
