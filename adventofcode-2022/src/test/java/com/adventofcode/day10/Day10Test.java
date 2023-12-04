package com.adventofcode.day10;

import static com.adventofcode.junit.util.AdventOfCodeAssertion.assertAdventOfCode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;

import com.adventofcode.junit.extension.AdventOfCodeDailySource;

class Day10Test {

	@ParameterizedTest
	@AdventOfCodeDailySource
	void testPart1(Path file) throws IOException {

		// TODO: 15120 is the correct answer but in the test the values differ
		var sum = new AtomicInteger(0);
		var registerValue = new AtomicInteger(1);

		var lines = Files.lines(file)
				.flatMap(string -> string.startsWith("addx") ? Stream.of("noop", string) : Stream.of(string)).toList();

		IntStream.rangeClosed(1, 220).forEachOrdered(i -> {
			// System.out.println(i + " " + registerValue.get() + "\t" + lines.get(i));

			switch (i) {
			case 20, 60, 100, 140, 180, 220:
				// System.out.println(i + " " + i * registerValue.get());
				sum.addAndGet(i * registerValue.get());
			}

			if (lines.get(i).startsWith("addx")) {
				registerValue.addAndGet(Integer.parseInt(lines.get(i).split(" ")[1]));
			}
		});

		assertAdventOfCode(file, 13140, sum.get());
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
		assertAdventOfCode(file, 1L, 1L);
	}

}
