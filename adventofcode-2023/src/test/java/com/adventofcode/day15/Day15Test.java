package com.adventofcode.day15;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.IntStream;

import org.junit.jupiter.params.ParameterizedTest;

import com.adventofcode.junit.extension.AdventOfCodeDailySource;
import com.adventofcode.junit.util.AdventOfCodeAssertion;

class Day15Test {

	@ParameterizedTest
	@AdventOfCodeDailySource
	void testPart1(Path file) throws IOException {
		String[] strings = Files.readAllLines(file, StandardCharsets.ISO_8859_1).get(0).split(",");
		AdventOfCodeAssertion.assertAdventOfCode(file, 1320,
				Arrays.stream(strings).mapToInt(Day15Test::hashAlgorithm).sum());
	}

	private static int hashAlgorithm(String value) {
		return value.codePoints().reduce(0, (left, right) -> ((left + right) * 17) % 256);
	}

	@ParameterizedTest
	@AdventOfCodeDailySource
	void testPart2(Path file) throws IOException {
		List<Lens> strings = Arrays.stream(Files.readAllLines(file, StandardCharsets.ISO_8859_1).get(0).split(","))
				.map(s -> Lens.toLens(s)).toList();

		Map<Integer, LinkedList<Lens>> hashmap = new HashMap<>();

		strings.forEach(l -> {
			int hash = hashAlgorithm(l.name);
			hashmap.computeIfAbsent(hash, k -> new LinkedList<>());
			if (l.shouldRemove()) {
				hashmap.get(hash).remove(l);
			} else if (hashmap.get(hash).contains(l)) {
				hashmap.get(hash).set(hashmap.get(hash).indexOf(l), l);
			} else {
				hashmap.get(hash).add(l);
			}
		});

		AdventOfCodeAssertion.assertAdventOfCode(file, 145,
				hashmap.entrySet().stream().flatMapToInt(e -> IntStream.range(0, e.getValue().size())
						.map(i -> computeFocusingPower(e.getKey(), i + 1, e.getValue().get(i)))).sum());
	}

	record Lens(String name, int focalLength) {

		public static Lens toLens(String input) {
			if (input.contains("-")) {
				return new Lens(input.substring(0, input.length() - 1), -1);
			} else {
				String[] split = input.split("=");
				return new Lens(split[0], Integer.parseInt(split[1]));
			}
		}

		public boolean shouldRemove() {
			return focalLength == -1;
		}

		@Override
		public int hashCode() {
			return Objects.hash(name);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Lens other = (Lens) obj;
			return Objects.equals(name, other.name);
		}

	}

	private int computeFocusingPower(int box, int index, Lens l) {
		return (box + 1) * index * l.focalLength;
	}

}
