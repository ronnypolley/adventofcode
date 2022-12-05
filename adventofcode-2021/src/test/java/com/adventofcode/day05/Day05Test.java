package com.adventofcode.day05;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.params.ParameterizedTest;

import com.adventofcode.junit.extension.AdventOfCodeDailySource;
import com.adventofcode.junit.util.AdventOfCodeAssertion;

class Day05Test {

	@ParameterizedTest
	@AdventOfCodeDailySource
	void testPart2(Path file) throws IOException {
		var max = new AtomicInteger();
		var list = Files.lines(file)
				.map(x -> Arrays
						.stream(x.replaceAll("(\\d{1,}),(\\d{1,}) -> (\\d{1,}),(\\d{1,})", "$1,$2,$3,$4").split(","))
						.mapToInt(Integer::parseInt).map(i -> {
							max.set(max.get() < i ? i : max.get());
							return i;
						}).toArray())
				.toList();

		var matrix = new int[max.get() + 1][max.get() + 1];

		list.stream().forEach(i -> {
			if (i[0] == i[2] || i[1] == i[3]) {
				for (var x = Math.min(i[0], i[2]); x <= Math.max(i[0], i[2]); x++) {
					for (var y = Math.min(i[1], i[3]); y <= Math.max(i[1], i[3]); y++) {
						matrix[y][x] += 1;
					}
				}
			} else {
				for (var x = Math.min(i[0], i[2]); x <= Math.max(i[0], i[2]); x++) {
					var ref = Math.min(i[0], i[2]) == i[0] ? i[1] : i[3];
					var other = Math.min(i[0], i[2]) == i[0] ? i[3] : i[1];
					matrix[ref + (other - ref) / Math.abs(other - ref) * (x - Math.min(i[0], i[2]))][x] += 1;
				}
			}
		});

		AdventOfCodeAssertion.assertAdventOfCode(file, 12,
				(int) Arrays.stream(matrix).flatMap(i -> Arrays.stream(i).boxed()).filter(i -> i >= 2).count());
	}

	@ParameterizedTest
	@AdventOfCodeDailySource
	void testPart1(Path file) throws IOException {
		var max = new AtomicInteger();
		var list = Files.lines(file)
				.map(x -> Arrays
						.stream(x.replaceAll("(\\d{1,}),(\\d{1,}) -> (\\d{1,}),(\\d{1,})", "$1,$2,$3,$4").split(","))
						.mapToInt(Integer::parseInt).map(i -> {
							max.set(max.get() < i ? i : max.get());
							return i;
						}).toArray())
				.toList();

		var matrix = new int[max.get() + 1][max.get() + 1];

		list.stream().filter(i -> i[0] == i[2] || i[1] == i[3]).forEach(i -> {
			for (var x = Math.min(i[0], i[2]); x <= Math.max(i[0], i[2]); x++) {
				for (var y = Math.min(i[1], i[3]); y <= Math.max(i[1], i[3]); y++) {
					matrix[y][x] += 1;
				}
			}
		});

		AdventOfCodeAssertion.assertAdventOfCode(file, 5,
				(int) Arrays.stream(matrix).flatMap(i -> Arrays.stream(i).boxed()).filter(i -> i >= 2).count());
	}

}
