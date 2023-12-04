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

class Day04Test {

	@ParameterizedTest
	@AdventOfCodeDailySource
	void test(Path file) throws IOException {
		Files.lines(file).map(s -> s.replaceAll("Game \\d+:", "")).map(s -> {
			String[] split = s.strip().split("|");
			Day04Game game = new Day04Game();
			game.winning = Arrays.stream(split[0].split(" ")).mapToInt(i -> Integer.valueOf(i.strip())).boxed()
					.collect(Collectors.toCollection(HashSet::new));
			game.having = Arrays.stream(split[1].split(" ")).mapToInt(i -> Integer.valueOf(i.strip())).boxed()
					.collect(Collectors.toCollection(HashSet::new));
			return game;
		}).map(g -> {
			g.winning.retainAll(g.having);
			return g.winning;
		});// .mapToInt(w -> 1 << );
	}

	class Day04Game {
		Set<Integer> winning;
		Set<Integer> having;
	}

}
