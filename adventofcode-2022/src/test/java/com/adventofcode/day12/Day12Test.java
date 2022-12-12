package com.adventofcode.day12;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;

import org.junit.jupiter.params.ParameterizedTest;

import com.adventofcode.junit.extension.AdventOfCodeDailySource;
import com.adventofcode.junit.util.AdventOfCodeAssertion;

class Day12Test {

	@ParameterizedTest
	@AdventOfCodeDailySource
	void testPart1(Path file) throws IOException {
		List<Position> startPositions = new ArrayList<>();
		var atomicEndposition = new AtomicReference<>(new Position(0, 0));
		var map = createInitialMap(Files.readAllLines(file));
		initMap(Files.readAllLines(file), startPositions, atomicEndposition, map, List.of('S'));

		var min = computePath(startPositions, atomicEndposition, map).min();

		AdventOfCodeAssertion.assertAdventOfCode(file, 31, min.getAsInt());
	}

	@ParameterizedTest
	@AdventOfCodeDailySource
	void testPart2(Path file) throws IOException {
		var map = createInitialMap(Files.readAllLines(file));
		List<Position> startPositions = new ArrayList<>();
		var atomicEndposition = new AtomicReference<>(new Position(0, 0));
		initMap(Files.readAllLines(file), startPositions, atomicEndposition, map, List.of('S', 'a'));

		var min = computePath(startPositions, atomicEndposition, map).min();

		AdventOfCodeAssertion.assertAdventOfCode(file, 29, min.getAsInt());
	}

	private IntStream computePath(List<Position> startPositions, AtomicReference<Position> atomicEndposition,
			char[][] map) {
		return startPositions.stream().mapToInt(startPosition -> {
			Map<Position, Integer> visited = new HashMap<>(Map.of(startPosition, 0));
			Queue<Position> queue = new LinkedBlockingQueue<>(List.of(startPosition));

			while (!queue.isEmpty()) {
				var position = queue.poll();
				var newPostitions = position.getNewPositions().stream()
						.filter(pos -> pos.x < map[0].length && pos.y < map.length)
						.filter(pos -> map[pos.y][pos.x] - map[position.y][position.x] <= 1)
						.filter(pos -> !visited.containsKey(pos)).toList();
				for (Position newPos : newPostitions) {
					visited.put(newPos, visited.get(position) + 1);
				}

				if (newPostitions.contains(atomicEndposition.get())) {
					break;
				}

				queue.addAll(newPostitions);
			}
			return visited.getOrDefault(atomicEndposition.get(), Integer.MAX_VALUE);
		});
	}

	private void initMap(List<String> input, List<Position> startPositions, AtomicReference<Position> atomicEndposition,
			char[][] map, List<Character> possibleStartingPositions) throws IOException {
		for (var i = 0; i < input.size(); i++) {
			for (var j = 0; j < input.get(0).length(); j++) {
				if (possibleStartingPositions.contains(input.get(i).charAt(j))) {
					startPositions.add(new Position(j + 1, i + 1));
					map[i + 1][j + 1] = 'a';
				} else if (input.get(i).charAt(j) == 'E') {
					atomicEndposition.set(new Position(j + 1, i + 1));
					map[i + 1][j + 1] = 'z';
				} else {
					map[i + 1][j + 1] = input.get(i).charAt(j);
				}
			}
		}
	}

	private char[][] createInitialMap(List<String> lines) {
		var map = new char[lines.size() + 2][lines.get(0).length() + 2];
		for (var i = 0; i < map.length; i++) {
			for (var j = 0; j < map[0].length; j++) {
				map[i][j] = 'z';
			}
		}
		return map;
	}

	record Position(int x, int y) {

		List<Position> getNewPositions() {
			return List
					.of(new Position(x - 1, y), new Position(x + 1, y), new Position(x, y - 1), new Position(x, y + 1))
					.stream().filter(pos -> pos.x != 0).filter(pos -> pos.y != 0).toList();
		}
	}

}
