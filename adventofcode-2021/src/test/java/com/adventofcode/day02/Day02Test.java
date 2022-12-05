package com.adventofcode.day02;

import static com.adventofcode.junit.util.AdventOfCodeAssertion.assertAdventOfCode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.params.ParameterizedTest;

import com.adventofcode.junit.extension.AdventOfCodeDailySource;

class Day02Test {

	@ParameterizedTest
	@AdventOfCodeDailySource
	void testPart1(Path file) throws IOException {
		var position = Files.lines(file).map(str -> {
			var split = str.split(" ");
			return switch (split[0]) {
			case "forward" -> new Position(0, Integer.parseInt(split[1]));
			case "down" -> new Position(Integer.parseInt(split[1]), 0);
			case "up" -> new Position(-Integer.parseInt(split[1]), 0);
			default -> new Position(0, 0);
			};

		}).reduce(new Position(0, 0), this::computePosition);

		assertAdventOfCode(file, 150, position.depth * position.horizontal);
	}

	record Position(int depth, int horizontal) {
	}

	private Position computePosition(Position position1, Position position2) {
		return new Position(position1.depth + position2.depth, position1.horizontal + position2.horizontal);
	}

	@ParameterizedTest
	@AdventOfCodeDailySource
	void testPart2(Path file) throws Exception {
		var position = Files.lines(file).map(str -> {
			var split = str.split(" ");
			return switch (split[0]) {
			case "forward" -> new PositionWithDepth(0, Integer.parseInt(split[1]), 0);
			case "down" -> new PositionWithDepth(0, 0, Integer.parseInt(split[1]));
			case "up" -> new PositionWithDepth(0, 0, -Integer.parseInt(split[1]));
			default -> new PositionWithDepth(0, 0, 0);
			};

		}).reduce(new PositionWithDepth(0, 0, 0), this::computePosition2);

		assertAdventOfCode(file, 900, position.depth * position.horizontal);
	}

	record PositionWithDepth(int depth, int horizontal, int aim) {
	}

	private PositionWithDepth computePosition2(PositionWithDepth position21, PositionWithDepth position22) {
		return new PositionWithDepth(position21.depth + position21.aim * position22.horizontal,
				position21.horizontal + position22.horizontal, position21.aim + position22.aim);
	}

}
