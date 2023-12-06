package com.adventofcode.day02;

import static com.adventofcode.junit.util.AdventOfCodeAssertion.assertAdventOfCode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.params.ParameterizedTest;

import com.adventofcode.junit.extension.AdventOfCodeDailySource;
import com.adventofcode.junit.util.AdventOfCodeAssertion;

class Day02Test {

	@ParameterizedTest
	@AdventOfCodeDailySource
	void testPart1(Path file) throws IOException {
		AdventOfCodeAssertion.assertAdventOfCode(file, 8, Files.lines(file).map(Day2Game::makeGame)
				.filter(game -> game.isPossible(12, 14, 13)).mapToInt(Day2Game::id).sum());
	}

	@ParameterizedTest
	@AdventOfCodeDailySource
	void testPart2(Path file) throws IOException {
		assertAdventOfCode(file, 2286, Files.lines(file).map(Day2Game::makeGame).map(Day2Game::minimalSetOfCubes)
				.mapToInt(game -> game.red() * game.green() * game.blue()).sum());
	}

}
