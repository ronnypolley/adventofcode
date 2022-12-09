package com.adventofcode.day09;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.IntStream;

import org.junit.jupiter.params.ParameterizedTest;

import com.adventofcode.junit.extension.AdventOfCodeDailySource;
import com.adventofcode.junit.util.AdventOfCodeAssertion;

class Day09Test {

	@ParameterizedTest
	@AdventOfCodeDailySource
	void testPart1(Path file) throws IOException {
		List<Position> knots = new ArrayList<>(IntStream.rangeClosed(0, 1).mapToObj(i -> new Position(0, 0)).toList());
		var visited = processMoving(file, knots);

		AdventOfCodeAssertion.assertAdventOfCode(file, 13, visited.size());
	}

	@ParameterizedTest
	@AdventOfCodeDailySource
	void testPart2(Path file) throws IOException {
		List<Position> knots = new ArrayList<>(IntStream.rangeClosed(0, 9).mapToObj(i -> new Position(0, 0)).toList());
		var visited = processMoving(file, knots);

		AdventOfCodeAssertion.assertAdventOfCode(file, 1, visited.size());
	}

	private Set<Position> processMoving(Path file, List<Position> knots) throws IOException {
		Set<Position> visited = new HashSet<>(List.of(knots.get(knots.size() - 1)));

		for (String command : Files.readAllLines(file)) {
			var split = command.split(" ");
			var amount = Integer.parseInt(split[1]);
			for (var i = 0; i < amount; i++) {
				switch (split[0]) {
				case "U":
					knots.set(0, knots.get(0).moveY(1));
					break;
				case "D":
					knots.set(0, knots.get(0).moveY(-1));
					break;
				case "L":
					knots.set(0, knots.get(0).moveX(-1));
					break;
				case "R":
					knots.set(0, knots.get(0).moveX(1));
					break;
				default:
					break;
				}

				IntStream.range(1, knots.size()).forEach(j -> knots.set(j, knots.get(j).follow(knots.get(j - 1))));
				visited.add(knots.get(knots.size() - 1));
			}
		}
		return visited;
	}

	record Position(int x, int y) {

		boolean isAdjacent(Position pos) {
			return Math.abs(x - pos.x) <= 1 && Math.abs(y - pos.y) <= 1;
		}

		Position move(String direction) {
			return new Position(direction.equals("L") ? x - 1 : direction.equals("R") ? x + 1 : 0,
					direction.equals("D") ? y - 1 : direction.equals("U") ? y + 1 : 0);
		}

		Position moveY(int move) {
			return new Position(x, y + move);
		}

		Position moveX(int move) {
			return new Position(x + move, y);
		}

		Position follow(Position pos) {
			if (!isAdjacent(pos)) {
				return new Position(x - pos.x == 0 ? x : x - (x - pos.x) / Math.abs(x - pos.x),
						y - pos.y == 0 ? y : y - (y - pos.y) / Math.abs(y - pos.y));
			}
			return this;
		}

		@Override
		public int hashCode() {
			final var prime = 31;
			var result = 1;
			return prime * result + Objects.hash(x, y);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null || getClass() != obj.getClass()) {
				return false;
			}
			var other = (Position) obj;
			return x == other.x && y == other.y;
		}

		@Override
		public String toString() {
			return "Position [x=" + x + ", y=" + y + "]";
		}

	}

}
