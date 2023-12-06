package com.adventofcode.day02;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class Day02Test {

	static String fileName = "src/test/resources/day02input";

	@ParameterizedTest
	@CsvSource(textBlock = """
			#X, Y, Z
			 1, 2, 3
			 1, 3, 2
			 2, 1, 3
			 2, 3, 1
			 3, 2, 1
			 3, 1, 2
			""")
	@Disabled("rewrote history to remove real input")
	void testPart1(int valueX, int valueY, int valueZ) throws IOException {
		System.out.println(Files.lines(Path.of(fileName)).map(str -> {
			var split = str.split(" ");
			var op = "A".equals(split[0]) ? 1 : "B".equals(split[0]) ? 2 : 3;
			var valueMe = "X".equals(split[1]) ? valueX : "Y".equals(split[1]) ? valueY : valueZ;
			return switch (op - valueMe) {
			case 0 -> 3 + valueMe;
			case -1 -> 6 + valueMe;
			case -2 -> /* rock (1) & scissor (3) */ 0 + valueMe;
			case 2 -> /* scissor (3) & rock (1) */ 6 + valueMe;
			case 1 -> /* scissor (3) & paper(2) */ /* paper (2) & rock(1) */ 0 + valueMe;
			default -> Integer.MAX_VALUE;
			};
		}).reduce(0, Integer::sum));
	}

	@ParameterizedTest
	@Disabled("rewrote history to exclude real input")
	void testPart2(Path file) throws Exception {
		System.out.println(Files.lines(file).map(str -> {
			var split = str.split(" ");
			var op = "A".equals(split[0]) ? 1 : "B".equals(split[0]) ? 2 : 3;
			return switch (split[1]) {
			case "X" -> op == 1 ? 3 : op == 2 ? 1 : 2;
			case "Y" -> 3 + (op == 1 ? 1 : op == 2 ? 2 : 3);
			case "Z" -> 6 + (op == 1 ? 2 : op == 2 ? 3 : 1);
			default -> Integer.MAX_VALUE;
			};
		}).reduce(0, Integer::sum));
	}
}
