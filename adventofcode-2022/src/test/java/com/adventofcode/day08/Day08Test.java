package com.adventofcode.day08;

import static com.adventofcode.junit.util.AdventOfCodeAssertion.assertAdventOfCode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.IntStream;

import org.junit.jupiter.params.ParameterizedTest;

import com.adventofcode.junit.extension.AdventOfCodeDailySource;

class Day08Test {

	@ParameterizedTest
	@AdventOfCodeDailySource
	void testPart1(Path file) throws IOException {
		var readAllLines = Files.lines(file).map(string -> string.chars().map(i -> i - 48).boxed().toList()).toList();

		readAllLines.forEach(line -> System.out.println(line));

		var count = 0;
		for (var y = 1; y < readAllLines.size() - 1; y++) {
			for (var x = 1; x < readAllLines.get(0).size() - 1; x++) {
				final var xUse = x;
				final var yUse = y;
				System.out.println();
				count += IntStream.range(0, 4).sorted().mapToLong(i -> switch (i) {
				case 0 -> IntStream.range(0, xUse).map(xInt -> readAllLines.get(yUse).get(xInt))
						.filter(current -> current >= readAllLines.get(yUse).get(xUse)).count();
				case 1 -> IntStream.range(xUse + 1, readAllLines.get(yUse).size()).boxed()
						.map(xInt -> readAllLines.get(yUse).get(xInt))
						.filter(current -> current >= readAllLines.get(yUse).get(xUse)).count();
				case 2 -> IntStream.range(0, yUse).map(yInt -> readAllLines.get(yInt).get(xUse))
						.filter(current -> current >= readAllLines.get(yUse).get(xUse)).count();
				case 3 ->
					IntStream.range(yUse + 1, readAllLines.size()).boxed().map(yInt -> readAllLines.get(yInt).get(xUse))
							.filter(current -> current >= readAllLines.get(yUse).get(xUse)).count();
				default -> 0;
				}).filter(i -> i == 0).count() > 0 ? 1 : 0;
			}
		}
		count += readAllLines.size() * 2 + readAllLines.get(0).size() * 2 - 4;

		assertAdventOfCode(file, 21, count);

	}

	@ParameterizedTest
	@AdventOfCodeDailySource
	void testPart2(Path file) throws IOException {
		var readAllLines = Files.lines(file).map(string -> string.chars().map(i -> i - 48).boxed().toList()).toList();

		readAllLines.forEach(line -> System.out.println(line));

		var count = 0;
		for (var y = 1; y < readAllLines.size() - 1; y++) {
			for (var x = 1; x < readAllLines.get(0).size() - 1; x++) {
				var maxX1 = 1;
				for (var x1 = x - 1; x1 > 0; x1--, maxX1++) {
					if (readAllLines.get(y).get(x1) >= readAllLines.get(y).get(x)) {
						break;
					}
				}
				var maxX2 = 1;
				for (var x1 = x + 1; x1 < readAllLines.get(y).size() - 1; x1++, maxX2++) {
					if (readAllLines.get(y).get(x1) >= readAllLines.get(y).get(x)) {
						break;
					}
				}
				var maxY1 = 1;
				for (var y1 = y - 1; y1 > 0; y1--, maxY1++) {
					if (readAllLines.get(y1).get(x) >= readAllLines.get(y).get(x)) {
						break;
					}
				}
				var maxY2 = 1;
				for (var y1 = y + 1; y1 < readAllLines.size() - 1; y1++, maxY2++) {
					if (readAllLines.get(y1).get(x) >= readAllLines.get(y).get(x)) {
						break;
					}
				}
				System.out.println(maxX1 + " " + maxX2 + " " + maxY1 + " " + maxY2);
				count = maxX1 * maxX2 * maxY1 * maxY2 > count ? maxX1 * maxX2 * maxY1 * maxY2 : count;
			}
		}

		assertAdventOfCode(file, 8, count);

	}

}
