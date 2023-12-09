package com.adventofcode.day08;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.jupiter.params.ParameterizedTest;

import com.adventofcode.junit.extension.AdventOfCodeDailySource;
import com.adventofcode.junit.util.AdventOfCodeAssertion;

class Day08Test {

	@ParameterizedTest
	@AdventOfCodeDailySource
	void testPart1(Path file) throws IOException {
		var lines = Files.readAllLines(file);
		var instructions = lines.get(0);

		var map = parseInputMap(lines);

		var finalList = new ArrayList<>(List.of("AAA"));
		var instructionIndex = 0;
		while (!finalList.get(finalList.size() - 1).equals("ZZZ")) {
			var mapLine = map.get(finalList.get(finalList.size() - 1));
			if (instructions.charAt(instructionIndex) == 'R') {
				finalList.add(mapLine.right);
			} else {
				finalList.add(mapLine.left);
			}

			instructionIndex = (instructionIndex + 1) % instructions.length();
		}

		AdventOfCodeAssertion.assertAdventOfCode(file, 2, finalList.size() - 1);
	}

	private Map<String, MapLine> parseInputMap(List<String> lines) {
		return lines.subList(2, lines.size()).stream().map(l -> {
			var split = l.split(" = ");
			var split2 = split[1].replace("(", "").replace(")", "").split(", ");
			return new MapLine(split[0], split2[0], split2[1]);
		}).collect(Collectors.toMap(ml -> ml.id, ml -> ml));
	}

	@ParameterizedTest
	@AdventOfCodeDailySource
	void testPart2(Path file) throws IOException {
		var lines = Files.readAllLines(file);
		var instructions = lines.get(0);

		var map = parseInputMap(lines);

		var finalMap = map.keySet().stream().filter(key -> key.endsWith("A"))
				.collect(Collectors.toMap(s -> s, Status::new));

		finalMap.values().parallelStream().forEach(status -> {
			var instructionIndex = 0;
			while (!status.last.endsWith("Z")) {
				var mapLine = map.get(status.last);
				if (instructions.charAt(instructionIndex) == 'R') {
					status.last = mapLine.right;
				} else {
					status.last = mapLine.left;
				}
				status.runs = status.runs.add(BigInteger.ONE);

				instructionIndex = (instructionIndex + 1) % instructions.length();
			}
		});

		var lcm = finalMap.values().stream().map(s -> s.runs).reduce(Day08Test::lcm).get();

		AdventOfCodeAssertion.assertAdventOfCode(file, 6L, lcm.longValue());
	}

	public static BigInteger lcm(BigInteger number1, BigInteger number2) {
		var gcd = number1.gcd(number2);
		var absProduct = number1.multiply(number2).abs();
		return absProduct.divide(gcd);
	}

	record MapLine(String id, String left, String right) {

	}

	class Status {
		BigInteger runs = BigInteger.ZERO;
		String last;

		public Status(String last) {
			this.last = last;
		}
	}

}
