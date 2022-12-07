package com.adventofcode.day07;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.junit.jupiter.params.ParameterizedTest;

import com.adventofcode.junit.extension.AdventOfCodeDailySource;
import com.adventofcode.junit.util.AdventOfCodeAssertion;

class Day07Test {

	@ParameterizedTest
	@AdventOfCodeDailySource
	void testPart1(Path file) throws IOException {
		Map<String, Long> result = new HashMap<>();

		computeSize(Files.readAllLines(file), result);

		AdventOfCodeAssertion.assertAdventOfCode(file, 95437L,
				result.values().stream().filter(l -> l <= 100000).reduce(Long::sum).orElse(0L));
	}

	@ParameterizedTest
	@AdventOfCodeDailySource
	void testPart2(Path file) throws IOException {
		Map<String, Long> result = new HashMap<>();

		computeSize(Files.readAllLines(file), result);

		AdventOfCodeAssertion.assertAdventOfCode(file, 24933642L, result.values().stream().mapToLong(l -> l)
				.filter(l -> 70000000 - result.get("/") + l >= 30000000).min().orElse(0));
	}

	private void computeSize(List<String> lines, Map<String, Long> result) {
		var directoryStack = new Stack<>();
		for (String string : lines) {
			if (string.startsWith("$ cd ")) {
				if (string.endsWith("..")) {
					var lastDir = directoryStack.pop();
					result.merge((String) directoryStack.peek(), result.get(lastDir), Long::sum);
				} else {
					var directory = string.substring(string.lastIndexOf(" ") + 1);
					directory = directory.equals("/") ? directory : ((String) directoryStack.peek()).concat(directory);
					result.computeIfAbsent(directory, key -> 0L);
					directoryStack.push(directory);
				}
			} else if (string.matches("^\\d.*")) {
				result.merge((String) directoryStack.peek(), Long.parseLong(string.replaceAll("^(\\d+) .*", "$1")),
						Long::sum);
			}
		}
		while (!directoryStack.isEmpty() && !"/".equals(directoryStack.peek())) {
			var lastDir = directoryStack.pop();
			result.merge((String) directoryStack.peek(), result.get(lastDir), Long::sum);
		}
	}
}
