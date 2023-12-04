package com.adventofcode.day10;

import static com.adventofcode.junit.util.AdventOfCodeAssertion.assertAdventOfCode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;

import com.adventofcode.junit.extension.AdventOfCodeDailySource;

class Day10Test {

	@ParameterizedTest
	@AdventOfCodeDailySource
	void testPart1(Path file) throws IOException {
		var sum = 0;
		var registerValue = 1;

		var lines = Files.readAllLines(file);
		var postponed = new LinkedList<Integer>();
		for (var i = 1; i <= 220; i++) {

			if (i < lines.size() && lines.get(i - 1).startsWith("addx")) {
				postponed.addAll(List.of(0, Integer.parseInt(lines.get(i - 1).split(" ")[1])));
			} else {
				postponed.add(0);
			}

			switch (i) {
			case 20, 60, 100, 140, 180, 220:
				sum += i * registerValue;
			}

			registerValue += postponed.removeFirst();

		}

		assertAdventOfCode(file, 13140, sum);
	}

	@ParameterizedTest
	@AdventOfCodeDailySource
	void testPart2(Path file) throws IOException {
		var registerValue = new AtomicInteger(1);

		var lines = Files.lines(file)
				.flatMap(string -> string.startsWith("addx") ? Stream.of("noop", string) : Stream.of(string)).toList();

		IntStream.range(0, 240).forEachOrdered(i -> {
			if (i % 40 == 0) {
				System.out.println();
			}
			System.out.print(IntStream.rangeClosed(registerValue.get() - 1, registerValue.get() + 1).boxed().toList()
					.contains(i % 40) ? "#" : ".");

			if (lines.get(i).startsWith("addx")) {
				registerValue.addAndGet(Integer.parseInt(lines.get(i).split(" ")[1]));
			}
		});
		System.out.println();
		assertAdventOfCode(file, 1L, 1L);
	}

}
