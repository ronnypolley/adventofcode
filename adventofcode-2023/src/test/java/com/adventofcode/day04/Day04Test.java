package com.adventofcode.day04;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;

import com.adventofcode.junit.extension.AdventOfCodeDailySource;
import com.adventofcode.junit.util.AdventOfCodeAssertion;

class Day04Test {

	@ParameterizedTest
	@AdventOfCodeDailySource
	void testPart1(Path file) throws IOException {
		AdventOfCodeAssertion.assertAdventOfCode(file, 13L, mapCards(file).map(Day04Game::retaining)
				.filter(w -> !w.isEmpty()).mapToLong(w -> 1L << w.size() - 1).sum());
	}

	@ParameterizedTest
	@AdventOfCodeDailySource
	void testPart2(Path file) throws IOException {
		var list = mapCards(file).toList();
		mapCards(file).forEach(g -> {
			if (!g.retaining().isEmpty()) {
				list.subList(g.id + 1, g.id + 1 + g.retaining().size()).forEach(l -> l.amount += list.get(g.id).amount);
			}
		});
		AdventOfCodeAssertion.assertAdventOfCode(file, 30L, list.stream().mapToLong(g -> g.amount).sum());
	}

	private Stream<Day04Game> mapCards(Path file) throws IOException {
		return Files.lines(file).map(s -> {
			var gameSep = s.split(":");
			var split = gameSep[1].strip().split("\\|");
			var game = new Day04Game();
			game.id = Integer.parseInt(gameSep[0].replaceAll("Card\\s+", "")) - 1;
			game.winning = Arrays.stream(split[0].split(" ")).filter(s1 -> !s1.isBlank())
					.mapToInt(i -> Integer.parseInt(i.strip())).boxed().collect(Collectors.toCollection(HashSet::new));
			game.having = Arrays.stream(split[1].split(" ")).filter(s1 -> !s1.isBlank())
					.mapToInt(i -> Integer.parseInt(i.strip())).boxed().collect(Collectors.toCollection(HashSet::new));
			return game;
		});
	}

	class Day04Game {
		int id;

		int amount = 1;

		Set<Integer> winning;
		Set<Integer> having;

		Set<Integer> retaining() {
			var set = new HashSet<>(winning);
			set.retainAll(having);
			return set;
		}

		@Override
		public String toString() {
			return id + 1 + " " + retaining();
		}
	}

}
