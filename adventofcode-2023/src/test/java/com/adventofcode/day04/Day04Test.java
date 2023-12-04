package com.adventofcode.day04;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.params.ParameterizedTest;

import com.adventofcode.junit.extension.AdventOfCodeDailySource;

class Day04Test {

	@ParameterizedTest
	@AdventOfCodeDailySource
	void test(Path file) throws IOException {
		Files.lines(file).map(s -> s.replaceAll("Game \\d+:", ""));
	}

	class Day04Game {

	}

}
