package com.adventofcode.day05;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.params.ParameterizedTest;

import com.adventofcode.junit.extension.AdventOfCodeDailySource;
import com.adventofcode.junit.util.AdventOfCodeAssertion;

class Day05Test {

	private List<String> inputStacks;
	private int amountOfStacks;
	private List<String> moveOperations;
	private List<Stack<Object>> stacks;

	@ParameterizedTest
	@AdventOfCodeDailySource
	void testPart1(Path file) throws IOException {
		prepareInput(Files.readAllLines(file));

		stacks = IntStream.range(0, amountOfStacks)
				.mapToObj(i -> inputStacks.stream().map(s -> s.substring(i * 4 + 1, i * 4 + 2))
						.filter(s -> !s.isBlank()).collect(Stack::new, Stack::push, Stack::addAll))
				.toList();

		moveOperations.forEach(move -> {
			var operation = getOperation(move);
			IntStream.range(0, operation[0])
					.forEach(i -> stacks.get(operation[2] - 1).push(stacks.get(operation[1] - 1).pop()));
		});

		AdventOfCodeAssertion.assertAdventOfCode(file, "CMZ",
				stacks.stream().map(s -> (String) s.peek()).collect(Collectors.joining()));
	}

	@ParameterizedTest
	@AdventOfCodeDailySource
	void testPart2(Path file) throws IOException {
		prepareInput(Files.readAllLines(file));

		var stacks = IntStream.range(0, amountOfStacks)
				.mapToObj(i -> inputStacks.stream().map(s -> s.substring(i * 4 + 1, i * 4 + 2))
						.filter(s -> !s.isBlank()).collect(ArrayList::new, List::add, List::addAll))
				.toList();

		moveOperations.forEach(move -> {
			var operation = getOperation(move);
			var from = stacks.get(operation[1] - 1);
			var startingIndex = from.size() - operation[0] < 0 ? 0 : from.size() - operation[0];
			var sublist = from.subList(startingIndex, from.size());
			stacks.get(operation[2] - 1).addAll(sublist);
			sublist.clear();
		});

		AdventOfCodeAssertion.assertAdventOfCode(file, "MCD",
				stacks.stream().map(s -> (String) s.get(s.size() - 1)).collect(Collectors.joining()));
	}

	private int[] getOperation(String move) {
		return Arrays.stream(move.replaceAll("move (\\d+) from (\\d+) to (\\d+)", "$1,$2,$3").split(","))
				.mapToInt(Integer::parseInt).toArray();
	}

	private List<String> getMoveOperations(List<String> lines) {
		return lines.stream().filter(s -> s.startsWith("move")).toList();
	}

	private int getAmountOfStacks(List<String> lines) {
		return (int) lines.stream().filter(s -> s.strip().startsWith("1"))
				.flatMapToInt(s -> Arrays.stream(s.strip().split("\\s+")).mapToInt(c -> Integer.parseInt(c.trim())))
				.count();
	}

	private List<String> getInputStacks(List<String> lines) {
		return lines.stream().filter(s -> s.contains("[")).collect(ArrayList::new, List::add, List::addAll);
	}

	private void prepareInput(List<String> lines) {
		inputStacks = getInputStacks(lines);
		Collections.reverse(inputStacks);
		amountOfStacks = getAmountOfStacks(lines);
		moveOperations = getMoveOperations(lines);
	}

}
