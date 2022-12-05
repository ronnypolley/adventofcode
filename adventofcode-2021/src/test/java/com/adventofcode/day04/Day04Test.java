package com.adventofcode.day04;

import static com.adventofcode.junit.util.AdventOfCodeAssertion.assertAdventOfCode;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;

import com.adventofcode.junit.extension.AdventOfCodeDailySource;

class Day04Test {

	@ParameterizedTest
	@AdventOfCodeDailySource
	void testPart1(Path file) throws IOException {
		var group = new AtomicInteger();
		Map<Integer, List<String>> map = Files.lines(file)
				.collect(Collectors.groupingBy(string -> string.isBlank() ? group.getAndIncrement() : group.get()));

		var input = Stream.of(map.get(0).get(0)).map(string -> string.split(","))
				.flatMap(t -> Arrays.stream(t).map(Integer::parseInt)).toList();

		var boards = map.values().stream().skip(1)
				.map(board -> board.stream().filter(string -> !string.isBlank())
						.map(string -> string.replaceAll("(\\d)\\s+", "$1,")).map(this::toListofInteger).toList())
				.map(Board::new).toList();

		for (Integer i : input) {
			var first = boards.stream().map(board -> board.mark(i)).filter(Board::bingo).findFirst();
			if (first.isPresent()) {
				assertAdventOfCode(file, 4512, first.get().value() * i);
				return;
			}
		}
		fail();
	}

	@ParameterizedTest
	@AdventOfCodeDailySource
	void testPart2(Path file) throws IOException {
		var group = new AtomicInteger();
		Map<Integer, List<String>> map = Files.lines(file)
				.collect(Collectors.groupingBy(string -> string.isBlank() ? group.getAndIncrement() : group.get()));

		var input = Stream.of(map.get(0).get(0)).map(string -> string.split(","))
				.flatMap(t -> Arrays.stream(t).map(Integer::parseInt)).toList();

		var boards = map.values().stream().skip(1)
				.map(board -> board.stream().filter(string -> !string.isBlank())
						.map(string -> string.replaceAll("(\\d)\\s+", "$1,")).map(this::toListofInteger).toList())
				.map(Board::new).toList();

		for (Integer i : input) {
			boards = boards.stream().map(board -> board.mark(i)).toList();
			var first = boards.stream().filter(Board::bingo).findFirst();
			boards = boards.stream().filter(b -> !b.bingo()).toList();
			if (first.isPresent() && boards.isEmpty()) {
				assertAdventOfCode(file, 1924, first.get().value() * i);
				return;
			}
		}
		fail();
	}

	class Board {

		List<List<Integer>> board = new ArrayList<>();

		Board(List<List<Integer>> input) {
			board.addAll(input);
			board.addAll(IntStream.range(0, input.size())
					.mapToObj(i -> input.stream().map(list -> list.get(i)).toList()).toList());
		}

		Board mark(Integer i) {
			board = board.stream().map(list -> list.stream().filter(el -> !el.equals(i)).toList()).toList();
			if (board.stream().flatMap(List::stream).anyMatch(e -> e.equals(i))) {
				throw new IllegalArgumentException();
			}
			return this;
		}

		boolean bingo() {
			return board.stream().filter(List::isEmpty).findFirst().map(List::isEmpty).orElse(false);
		}

		int value() {
			return board.stream().limit(5).map(list -> list.stream().reduce(0, Integer::sum)).reduce(0, Integer::sum);
		}
	}

	private <R extends List<Integer>> List<Integer> toListofInteger(String string1) {
		return Arrays.stream(string1.split(",")).map(String::trim).map(Integer::parseInt).toList();
	}

}
