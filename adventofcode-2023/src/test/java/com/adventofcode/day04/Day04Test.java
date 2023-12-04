package com.adventofcode.day04;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;

import com.adventofcode.junit.extension.AdventOfCodeDailySource;
import com.adventofcode.junit.util.AdventOfCodeAssertion;

class Day04Test {

	@ParameterizedTest
	@AdventOfCodeDailySource
	void testPart1(Path file) throws IOException {
		// Computing the value per card is achieved by bit shifting, as it is the most
		// efficent way.
		AdventOfCodeAssertion.assertAdventOfCode(file, 13L, mapCards(file).map(Day04Game::retaining)
				.filter(w -> !w.isEmpty()).mapToLong(w -> 1L << w.size() - 1).sum());
	}

	@ParameterizedTest
	@AdventOfCodeDailySource
	void testPart2(Path file) throws IOException {
		var list = mapCards(file).toList();
		mapCards(file).forEach(g -> {
			if (!g.retaining().isEmpty()) {
				// If we have matching numbers, we look into the already created list and add to
				// each possible card the amount the current card was found.
				list.subList(g.id + 1, g.id + 1 + g.retaining().size()).forEach(l -> l.amount += list.get(g.id).amount);
			}
		});
		AdventOfCodeAssertion.assertAdventOfCode(file, 30L, list.stream().mapToLong(g -> g.amount).sum());
	}

	private Stream<Day04Game> mapCards(Path file) throws IOException {
		return Files.lines(file).map(s -> {
			var matcher = Pattern.compile("Card\\s*(\\d+):(.+)\\|(.+)").matcher(s);
			matcher.find();

			return new Day04Game(Integer.parseInt(matcher.group(1)) - 1, createNumberList(matcher.group(2)),
					createNumberList(matcher.group(3)));
		});
	}

	private HashSet<Integer> createNumberList(String string) {
		return Arrays.stream(string.split(" ")).filter(s1 -> !s1.isBlank()).mapToInt(i -> Integer.parseInt(i.strip()))
				.boxed().collect(Collectors.toCollection(HashSet::new));
	}

	class Day04Game {
		int id;

		int amount = 1;

		Set<Integer> winning;
		Set<Integer> having;

		public Day04Game(int i, HashSet<Integer> createNumberList, HashSet<Integer> createNumberList2) {
			this.id = i;
			this.winning = createNumberList;
			this.having = createNumberList2;
		}

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
