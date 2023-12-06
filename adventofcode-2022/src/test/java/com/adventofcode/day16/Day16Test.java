package com.adventofcode.day16;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;

import com.adventofcode.junit.extension.AdventOfCodeDailySource;
import com.adventofcode.junit.util.AdventOfCodeAssertion;

class Day16Test {

	@ParameterizedTest
	@AdventOfCodeDailySource
	@Disabled
	void test(Path file) throws IOException {
		Map<String, ValveNode> hashMap = Files.lines(file).flatMap(string -> {
			var split = string
					.replaceAll("Valve (\\w+) has flow rate=(\\d+); tunnels? leads? to valves? (.*)", "$1;$2;$3")
					.split(";");
			return Map.of(split[0], new ValveNode(Integer.parseInt(split[1]), split[2])).entrySet().stream();
		}).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (x, y) -> y, HashMap::new));

		var max = makeAStep(30, hashMap, "AA", Collections.emptyMap()).stream().mapToInt(i -> i).max();

		AdventOfCodeAssertion.assertAdventOfCode(file, 1651, max.getAsInt());

	}

	List<Integer> makeAStep(int timeLeft, Map<String, ValveNode> input, String currentPosition,
			Map<String, ValveNode> openValves) {
		if (timeLeft == 0) {
			return List.of(openValves.values().stream().mapToInt(node -> node.preassure).sum());
		}

		System.out.println("BEGIN " + timeLeft + " " + currentPosition + " " + openValves);

		List<Integer> stepWithOpeningValve = new ArrayList<>();
		if (!openValves.containsKey(currentPosition)) {
			// opening the current valve
			var newOpenValves = new HashMap<>(openValves);
			newOpenValves.put(currentPosition, input.get(currentPosition));
			stepWithOpeningValve.addAll(makeAStep(timeLeft - 1, input, currentPosition, newOpenValves));
		}

		var listFromSteps = input.get(currentPosition).adjacentNodes.stream()
				.filter(node -> !openValves.containsKey(node))
				.flatMap(node -> makeAStep(timeLeft - 1, input, node, openValves).stream()).toList();

		stepWithOpeningValve.addAll(listFromSteps);
		final var sum = openValves.values().stream().mapToInt(node -> node.preassure).sum();
		return stepWithOpeningValve.stream().map(i -> i + sum).toList();
	}

	class ValveNode {
		int preassure;
		Set<String> adjacentNodes = new HashSet<>();

		public ValveNode(int preassure, String adjacentNodes) {
			this.preassure = preassure;
			this.adjacentNodes.addAll(Arrays.asList(adjacentNodes.split(", ")));
		}

		@Override
		public String toString() {
			return "ValveNode [preassure=" + preassure + ", adjacentNodes=" + adjacentNodes + "]";
		}

	}

}
