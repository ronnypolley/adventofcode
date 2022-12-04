package com.adventofcode.day03;

import static com.adventofcode.junit.util.AdventOfCodeAssertion.assertAdventOfCode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.junit.jupiter.params.ParameterizedTest;

import com.adventofcode.junit.extension.AdventOfCodeDailySource;

class Day03Test {

	@ParameterizedTest
	@AdventOfCodeDailySource
	void testPart1(Path filename) throws IOException {
		var reduce = Files.lines(filename).map(rucksack -> {
			List<Integer> firstHalf = new ArrayList<>(rucksack.chars().boxed().limit(rucksack.length() / 2).toList());
			var secondHalf = rucksack.chars().boxed().skip(rucksack.length() / 2).toList();
			firstHalf.retainAll(secondHalf);
			return firstHalf.get(0);
		}).map(ch -> ch > 97 ? ch - 96 : ch - 64 + 26).reduce(Integer::sum);

		assertAdventOfCode(filename, 157, reduce.get());
	}

	@ParameterizedTest
	@AdventOfCodeDailySource
	void testPart2(Path filename) throws IOException {
		var counter = new AtomicInteger();
		var reduce = Files.lines(filename).collect(Collectors.groupingBy(x -> counter.getAndIncrement() / 3)).values()
				.stream().map(group -> {

					List<Integer> firstElve = new ArrayList<>(group.get(0).chars().boxed().toList());
					firstElve.retainAll(group.get(1).chars().boxed().toList());
					firstElve.retainAll(group.get(2).chars().boxed().toList());
					return firstElve.get(0);
				}).map(ch -> ch > 97 ? ch - 96 : ch - 64 + 26).reduce(Integer::sum);

		assertAdventOfCode(filename, 70, reduce.get());
	}

}
