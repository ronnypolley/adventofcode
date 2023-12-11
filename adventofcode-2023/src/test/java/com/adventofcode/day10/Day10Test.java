package com.adventofcode.day10;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.params.ParameterizedTest;

import com.adventofcode.junit.extension.AdventOfCodeDailySource;
import com.adventofcode.junit.util.AdventOfCodeAssertion;

class Day10Test {

	Set<String> westPossible1 = Set.of("-", "L", "F");
	Set<String> eastPossible1 = Set.of("-", "J", "7");
	Set<String> southPossible1 = Set.of("|", "L", "J");
	Set<String> northPossible1 = Set.of("|", "7", "F");

	Set<String> westPossible2 = Set.of("|", "J", "7", "O", ".");
	Set<String> eastPossible2 = Set.of("L", "F", "|", "O", ".");
	Set<String> southPossible2 = Set.of("-", "7", "F", "O", ".");
	Set<String> northPossible2 = Set.of("-", "J", "L", "O", ".");

	@ParameterizedTest
	@AdventOfCodeDailySource
	void testPart1(Path file) throws IOException {
		List<List<String>> lines = Files.lines(file)
				.map(l -> Arrays.stream(l.split("")).collect(Collectors.toCollection(ArrayList::new)))
				.collect(Collectors.toCollection(ArrayList::new));

		for (int i = 0; i < lines.size(); i++) {
			for (int j = 0; j < lines.get(i).size(); j++) {
				if (!lines.get(i).get(j).equals("S")) {
					continue;
				}

				lines.get(i).set(j, "O");
				goAlongPipe(i, j, lines);
			}
		}

//		lines.forEach(s -> System.out.println(Arrays.asList(s)));
		AdventOfCodeAssertion.assertAdventOfCode(file, 8L,
				lines.stream().mapToLong(l -> l.stream().filter(s -> s.equals("O")).count()).sum() / 2 + 1);
	}

	@ParameterizedTest
	@AdventOfCodeDailySource
	void testPart2(Path file) throws IOException {
		List<List<String>> lines = Files.lines(file)
				.map(l -> Arrays.stream(l.split("")).collect(Collectors.toCollection(ArrayList::new)))
				.collect(Collectors.toCollection(ArrayList::new));

		for (int i = 0; i < lines.size(); i++) {
			for (int j = 0; j < lines.get(i).size(); j++) {
				if (!lines.get(i).get(j).equals("S")) {
					continue;
				}

				lines.get(i).set(j, "O");
				// pipe ready
				goAlongPipe(i, j, lines);
				markEnclosed(lines);
			}
		}

		lines.forEach(s -> System.out.println(Arrays.asList(s)));
		AdventOfCodeAssertion.assertAdventOfCode(file, 8L,
				lines.stream().mapToLong(l -> l.stream().filter(s -> s.equals("I")).count()).sum());
	}

	private void markEnclosed(List<List<String>> lines) {
		for (int i = 0; i < lines.size(); i++) {
			for (int j = 0; j < lines.get(i).size(); j++) {
				if (lines.get(i).get(j).equals(".")) {
					HashSet<Coordinate> set = new HashSet<Coordinate>();
					if (checkAround(i, j, lines, set)) {
						if (set.stream()
								.map(c -> List
										.of(lines.get(c.i - 1).get(c.j), lines.get(c.i + 1).get(c.j),
												lines.get(c.i).get(c.j - 1), lines.get(c.i).get(c.j + 1))
										.stream().anyMatch(s -> s.equals("O")))
								.anyMatch(b -> b == true)) {

							set.stream().forEach(c -> lines.get(c.i).set(c.j, "I"));
						}
					}
				}
			}
		}
	}

	private boolean checkAround(int i, int j, List<List<String>> lines, HashSet<Coordinate> set) {
		if (set.contains(new Coordinate(i, j))) {
			return true;
		}

		if (i == 0 || i == lines.size() - 1 || j == 0 || j == lines.get(i).size() - 1) {
			return false;
		}

		if (checkWest(i, j, lines) && checkEast(i, j, lines) && checkNorth(i, j, lines) && checkSouth(i, j, lines)) {
			if (lines.get(i).get(j).equals(".")) {
				set.add(new Coordinate(i, j));
				var result = true;
				if (i - 1 >= 0 && lines.get(i - 1).get(j).equals(".")) {
					result &= checkAround(i - 1, j, lines, set);
				}
				if (i + 1 < lines.size() && lines.get(i + 1).get(j).equals(".")) {
					result &= checkAround(i + 1, j, lines, set);
				}
				if (j - 1 >= 0 && lines.get(i).get(j - 1).equals(".")) {
					result &= checkAround(i, j - 1, lines, set);
				}
				if (j + 1 < lines.get(i).size() && lines.get(i).get(j + 1).equals(".")) {
					result &= checkAround(i, j + 1, lines, set);
				}
				return result;
			}
			return true;

		}
		return false;
	}

	private int goAlongPipe(int i, int j, List<List<String>> lines) {

		goNorth(i - 1, j, lines, "O");
		goSouth(i + 1, j, lines, "O");
		goEast(i, j + 1, lines, "O");
		goWest(i, j - 1, lines, "O");

		return 1;
	}

	private void goWest(int i, int j, List<List<String>> lines, String marker) {
		if (j <= 0) {
			return;
		}

		if (!westPossible1.contains(lines.get(i).get(j))) {
			return;
		}

		String pipe = lines.get(i).get(j);
		lines.get(i).set(j, marker);
		switch (pipe) {
		case "-":
			goWest(i, j - 1, lines, marker);
			break;
		case "F":
			goSouth(i + 1, j, lines, marker);
			break;
		default:
			goNorth(i - 1, j, lines, marker);
			break;
		}
	}

	private boolean checkWest(int i, int j, List<List<String>> lines) {
		return j > 0 && westPossible2.contains(lines.get(i).get(j));
	}

	private boolean checkEast(int i, int j, List<List<String>> lines) {
		return j < lines.get(i).size() && eastPossible2.contains(lines.get(i).get(j));
	}

	private boolean checkNorth(int i, int j, List<List<String>> lines) {
		return j > 0 && northPossible2.contains(lines.get(i).get(j));
	}

	private boolean checkSouth(int i, int j, List<List<String>> lines) {
		return j < lines.size() && southPossible2.contains(lines.get(i).get(j));
	}

	private void goEast(int i, int j, List<List<String>> lines, String marker) {
		if (j >= lines.get(i).size()) {
			return;
		}

		String pipe = lines.get(i).get(j);
		if (!eastPossible1.contains(pipe)) {
			return;
		}

		lines.get(i).set(j, marker);
		switch (pipe) {
		case "-":
			goEast(i, j + 1, lines, marker);
			break;
		case "7":
			goSouth(i + 1, j, lines, marker);
			break;
		default:
			goNorth(i - 1, j, lines, marker);
			break;
		}
	}

	private void goSouth(int i, int j, List<List<String>> lines, String marker) {
		if (i >= lines.size()) {
			return;
		}

		if (!southPossible1.contains(lines.get(i).get(j))) {
			return;
		}

		String pipe = lines.get(i).get(j);
		lines.get(i).set(j, marker);
		switch (pipe) {
		case "|":
			goSouth(i + 1, j, lines, marker);
			break;
		case "L":
			goEast(i, j + 1, lines, marker);
			break;
		default:
			goWest(i, j - 1, lines, marker);
			break;
		}
	}

	private void goNorth(int i, int j, List<List<String>> lines, String marker) {
		if (i <= 0) {
			return;
		}

		if (!northPossible1.contains(lines.get(i).get(j))) {
			return;
		}

		String pipe = lines.get(i).get(j);
		lines.get(i).set(j, marker);
		switch (pipe) {
		case "|":
			goNorth(i - 1, j, lines, marker);
			break;
		case "7":
			goWest(i, j - 1, lines, marker);
			break;
		default:
			goEast(i, j + 1, lines, marker);
			break;
		}
	}

	record Coordinate(int i, int j) {
	}
}
