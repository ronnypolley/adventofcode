package com.adventofcode.day03;

import static com.adventofcode.junit.util.AdventOfCodeAssertion.assertAdventOfCode;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.stream.IntStream;

import org.junit.jupiter.params.ParameterizedTest;

import com.adventofcode.junit.extension.AdventOfCodeDailySource;

class Day03Test {

	@ParameterizedTest
	@AdventOfCodeDailySource
	void testPart1(Path file) throws Exception {
		var lines = Files.readAllLines(file);
		var string2 = IntStream.range(0, lines.get(0).length())
				.mapToObj(i -> lines.stream().map(string -> string.charAt(i)).map(x -> x == 48 ? 0 : 1).reduce(0,
						(sum, cur) -> sum += cur))
				.map(sum -> sum >= lines.size() / 2 ? 1 : 0)
				.collect(StringBuilder::new, StringBuilder::append, StringBuilder::append).toString();
		var parseInt = Integer.parseUnsignedInt(string2, 2);

		assertAdventOfCode(file, 198, parseInt
				* Integer.parseUnsignedInt(Integer.toBinaryString(~parseInt).substring(32 - string2.length()), 2));
	}

	@ParameterizedTest
	@AdventOfCodeDailySource
	void testPart2(Path file) throws Exception {
		var lines = Files.readAllLines(file);
		var linesCO2 = new ArrayList<>(lines);

		IntStream.range(0, lines.get(0).length()).forEach(i -> {
			lines.removeIf(string -> lines.size() > 1
					&& string.charAt(i) == 48 + (lines.stream().mapToInt(stringIn -> stringIn.charAt(i) - 48)
							.filter(ch -> ch == 1).count() >= Math.ceil(lines.size() / 2.0D) ? 0 : 1));
		});

		IntStream.range(0, linesCO2.get(0).length()).forEach(i -> {
			linesCO2.removeIf(string -> linesCO2.size() > 1
					&& string.charAt(i) == 48 + (linesCO2.stream().mapToInt(stringIn -> stringIn.charAt(i) - 48)
							.filter(ch -> ch == 1).count() < Math.ceil(linesCO2.size() / 2.0D) ? 0 : 1));
		});

		assertAdventOfCode(file, 230, Integer.parseInt(lines.get(0), 2) * Integer.parseInt(linesCO2.get(0), 2));
	}

}
