package com.adventofcode.day06;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.LongStream;

import org.junit.jupiter.params.ParameterizedTest;

import com.adventofcode.junit.extension.AdventOfCodeDailySource;
import com.adventofcode.junit.util.AdventOfCodeAssertion;

class Day06Test {

	@ParameterizedTest
	@AdventOfCodeDailySource
	void testPart1(Path file) throws IOException {
		var list = Files.lines(file).map(l -> Arrays.asList(l.replaceFirst(".*:", "").strip().split(" ")))
				.map(l -> l.stream().filter(s -> !s.isBlank()).mapToLong(Long::parseLong).boxed().toList()).toList();
		var races = new ArrayList<Race>();
		for (var i = 0; i < list.get(0).size(); i++) {
			races.add(new Race(list.get(0).get(i), list.get(1).get(i)));
		}

		AdventOfCodeAssertion
				.assertAdventOfCode(file, 288L,
						races.stream()
								.mapToLong(value -> LongStream.rangeClosed(0, value.time)
										.map(t -> ((value.time - t) * t)).filter(d -> value.distance < d).count())
								.reduce((i, j) -> i * j).getAsLong());
	}

	@ParameterizedTest
	@AdventOfCodeDailySource
	void testPart2(Path file) throws IOException {
		var list = Files.lines(file).map(l -> Arrays.asList(l.replaceFirst(".*:", "").strip().replaceAll("\\s*", "")))
				.map(l -> l.stream().mapToLong(Long::parseLong).boxed().toList()).toList();
		var race = new Race(list.get(0).get(0), list.get(1).get(0));

		AdventOfCodeAssertion.assertAdventOfCode(file, 71503L, LongStream.rangeClosed(0, race.time)
				.map(t -> ((race.time - t) * t)).filter(d -> race.distance < d).count());
	}

	record Race(Long time, Long distance) {
	}

}
