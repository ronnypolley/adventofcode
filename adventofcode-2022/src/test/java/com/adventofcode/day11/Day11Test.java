package com.adventofcode.day11;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.params.ParameterizedTest;

import com.adventofcode.junit.extension.AdventOfCodeDailySource;
import com.adventofcode.junit.util.AdventOfCodeAssertion;

class Day11Test {

	@ParameterizedTest
	@AdventOfCodeDailySource
	void testPart1(Path file) throws IOException {
		var monkeys = prepareMonkeys(file);

		IntStream.range(0, 20).forEach(i -> {
			monkeys.forEach(monkey -> {
				monkey.items.forEach(item -> {
					var newValueOf = monkey.newValueOf(item);
					monkeys.get(monkey.getTarget(newValueOf)).items.add(newValueOf);
				});
				monkey.items.clear();
			});

		});

		long sum = monkeys.stream().map(monkey -> monkey.inspected).sorted(Comparator.reverseOrder()).limit(2)
				.reduce((x, y) -> x * y).get();

		AdventOfCodeAssertion.assertAdventOfCode(file, 10605L, sum);
	}

	@ParameterizedTest
	@AdventOfCodeDailySource
	void testPart2(Path file) throws IOException {
		var monkeys = prepareMonkeys2(file);

		IntStream.range(0, 10000).forEach(i -> {
			monkeys.forEach(monkey -> {
				monkey.items.forEach(item -> {
					var newValueOf = monkey.newValueOf(item);
					monkeys.get(monkey.getTarget(newValueOf)).addItem(newValueOf);
				});
				monkey.items.clear();
			});

		});

		long sum = monkeys.stream().map(monkey -> monkey.inspected).sorted(Comparator.reverseOrder()).limit(2)
				.reduce((x, y) -> x * y).get();
		AdventOfCodeAssertion.assertAdventOfCode(file, 52166 * 52013L, sum);
	}

	private List<Monkey> prepareMonkeys(Path file) throws IOException {
		List<Monkey> monkeys = new ArrayList<>();
		var lines = Files.readAllLines(file);
		for (var i = 0; i < lines.size(); i += 7) {
			var monkey = new Monkey();
			monkey.setItems(Arrays.stream(lines.get(i + 1).substring(lines.get(i + 1).lastIndexOf(":") + 1).split(","))
					.map(String::trim).map(Integer::parseInt).toList());
			monkey.setOperation(lines.get(i + 2).substring(lines.get(i + 2).lastIndexOf("=") + 1).trim().split(" "));
			monkey.setDevisable(Integer.parseInt(lines.get(i + 3).replaceAll(".*Test: divisible by (\\d+)", "$1")));
			monkey.throwToIfTrue = Integer.parseInt(lines.get(i + 4).replaceAll(".*throw to monkey (\\d+)", "$1"));
			monkey.throwToIfFalse = Integer.parseInt(lines.get(i + 5).replaceAll(".*throw to monkey (\\d+)", "$1"));
			monkeys.add(monkey);
		}

		return monkeys;
	}

	private List<Monkey2> prepareMonkeys2(Path file) throws IOException {
		List<Monkey2> monkeys = new ArrayList<>();
		var lines = Files.readAllLines(file);
		for (var i = 0; i < lines.size(); i += 7) {
			var monkey = new Monkey2();
			monkey.setDevisable(Integer.parseInt(lines.get(i + 3).replaceAll(".*Test: divisible by (\\d+)", "$1")));
			monkey.setItems(Arrays.stream(lines.get(i + 1).substring(lines.get(i + 1).lastIndexOf(":") + 1).split(","))
					.map(String::trim).map(Integer::parseInt).toList());
			monkey.setOperation(lines.get(i + 2).substring(lines.get(i + 2).lastIndexOf("=") + 1).trim().split(" "));
			monkey.throwToIfTrue = Integer.parseInt(lines.get(i + 4).replaceAll(".*throw to monkey (\\d+)", "$1"));
			monkey.throwToIfFalse = Integer.parseInt(lines.get(i + 5).replaceAll(".*throw to monkey (\\d+)", "$1"));
			monkeys.add(monkey);
		}

		var reduce = monkeys.stream().mapToInt(monkey -> monkey.devisable).reduce((x, y) -> x * y).getAsInt();
		monkeys.forEach(monkey -> monkey.newValueDivisor = reduce);

		return monkeys;
	}

	class Monkey {
		List<Long> items = new ArrayList<>();
		int devisable;
		int throwToIfTrue;
		int throwToIfFalse;
		String[] operation;

		long inspected = 0;

		public Monkey() {
		}

		public void addItem(long newValueOf) {
			items.add(newValueOf);
		}

		public int getTarget(long newValueOf) {
			return newValueOf % devisable == 0 ? throwToIfTrue : throwToIfFalse;
		}

		public long newValueOf(long item) {
			inspected++;
			var result = switch (operation[2]) {
			case "old" -> item;
			default -> Integer.parseInt(operation[2]);
			};
			return switch (operation[1]) {
			case "+" -> item + result;
			case "*" -> item * result;

			default -> 0L;
			} / 3;
		}

		@Override
		public String toString() {
			return "Monkey [items=" + items + "]";
		}

		public void setItems(List<Integer> items) {
			items.forEach(this::addItem);
		}

		public void setDevisable(int devisable) {
			this.devisable = devisable;
		}

		public void setThrowToIfTrue(int throwToIfTrue) {
			this.throwToIfTrue = throwToIfTrue;
		}

		public void setThrowToIfFalse(int throwToIfFalse) {
			this.throwToIfFalse = throwToIfFalse;
		}

		public void setOperation(String[] operation) {
			this.operation = operation;
		}

	}

	class Monkey2 extends Monkey {

		int newValueDivisor;

		public Monkey2() {
		}

		@Override
		public long newValueOf(long item) {
			inspected++;
			var result = switch (operation[2]) {
			case "old" -> item;
			default -> Integer.parseInt(operation[2]);
			};
			return switch (operation[1]) {
			case "+" -> item + result;
			case "*" -> item * result;

			default -> 0L;
			} % newValueDivisor;
		}
	}
}
