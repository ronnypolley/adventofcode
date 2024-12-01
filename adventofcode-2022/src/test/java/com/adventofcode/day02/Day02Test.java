package com.adventofcode.day02;

import com.adventofcode.junit.extension.AdventOfCodeDailySource;
import com.adventofcode.junit.util.AdventOfCodeAssertion;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.params.ParameterizedTest;

class Day02Test {

	@ParameterizedTest
	@AdventOfCodeDailySource
	void testPart1(Path fileName) throws IOException {
      try (var lines = Files.lines(fileName)){
        AdventOfCodeAssertion.assertAdventOfCode(fileName, 15, lines.map(str -> {
            var split = str.split(" ");
            var op = "A".equals(split[0]) ? 1 : "B".equals(split[0]) ? 2 : 3;
            var valueMe = "X".equals(split[1]) ? 1 : "Y".equals(split[1]) ? 2 : 3;
            return switch (op - valueMe) {
            case 0 -> 3 + valueMe;
            case -1 -> 6 + valueMe;
            case -2 -> /* rock (1) & scissor (3) */ valueMe;
            case 2 -> /* scissor (3) & rock (1) */ 6 + valueMe;
            case 1 -> /* scissor (3) & paper(2) */ /* paper (2) & rock(1) */ valueMe;
            default -> Integer.MAX_VALUE;
            };
        }).reduce(Integer::sum).orElseThrow());
      }
    }

	@ParameterizedTest
	@AdventOfCodeDailySource
	void testPart2(Path file) throws Exception {
		try (var lines = Files.lines(file)) {
			AdventOfCodeAssertion.assertAdventOfCode(file, 12, lines.map(str -> {
				var split = str.split(" ");
				var op = "A".equals(split[0]) ? 1 : "B".equals(split[0]) ? 2 : 3;
				return switch (split[1]) {
					case "X" -> op == 1 ? 3 : op == 2 ? 1 : 2;
					case "Y" -> 3 + (op == 1 ? 1 : op == 2 ? 2 : 3);
					case "Z" -> 6 + (op == 1 ? 2 : op == 2 ? 3 : 1);
					default -> Integer.MAX_VALUE;
				};
			}).reduce(Integer::sum).orElseThrow());
		}
	}
}
