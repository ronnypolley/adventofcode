package com.adventofcode.day04;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.params.ParameterizedTest;

import com.adventofcode.junit.extension.AdventOfCodeDailySource;
import com.adventofcode.junit.util.AdventOfCodeAssertion;

class Day04Test {

	@ParameterizedTest
	@AdventOfCodeDailySource
	void test(Path file) throws IOException {
		long sum = Files.lines(file).map(s -> s.replaceAll("Card.*:", "")).map(s -> {
			String[] split = s.strip().split("\\|");
			Day04Game game = new Day04Game();
			game.winning = Arrays.stream(split[0].split(" ")).filter(s1 -> !s1.isBlank())
					.mapToInt(i -> Integer.valueOf(i.strip())).boxed().collect(Collectors.toCollection(HashSet::new));
			game.having = Arrays.stream(split[1].split(" ")).filter(s1 -> !s1.isBlank())
					.mapToInt(i -> Integer.valueOf(i.strip())).boxed().collect(Collectors.toCollection(HashSet::new));
			return game;
		}).map(g -> {
			g.winning.retainAll(g.having);
			return g.winning;
		}).mapToLong(w -> 1L << (w.size() - 1)).sum();

		AdventOfCodeAssertion.assertAdventOfCode(file, 13L, sum);
	}

	class Day04Game {
		Set<Integer> winning;
		Set<Integer> having;
	}

}
