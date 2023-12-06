package com.adventofcode.day14;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import org.junit.jupiter.params.ParameterizedTest;

import com.adventofcode.junit.extension.AdventOfCodeDailySource;

class Day14Test {

	@ParameterizedTest
	@AdventOfCodeDailySource
	void test(Path file) throws IOException {
		var map = Files.lines(file).flatMap(line -> {
			var result = new ArrayList<>(Arrays.stream(line.split(" -> ")).map(entry -> {
				var posValues = entry.split(",");
				return new Position(Integer.parseInt(posValues[0]), Integer.parseInt(posValues[1]));
			}).toList());

			for (var i = 1; i < result.size(); i++) {
				if (result.get(i - 1).x == result.get(i).x) {
					for (var y = Math.min(result.get(i - 1).y, result.get(i).y) + 1; y < Math.max(result.get(i - 1).y,
							result.get(i).y); y++) {
						result.add(new Position(result.get(i).x, y));
					}
				} else {
					for (var x = Math.min(result.get(i - 1).x, result.get(i).x) + 1; x < Math.max(result.get(i - 1).x,
							result.get(i).x); x++) {
						result.add(new Position(x, result.get(i).y));
					}
				}
			}
			return result.stream();
		}).toList();
		var set = new HashSet<>(map);
		System.out.println(set.size());

		// fail("Not yet implemented");
	}

	record Position(int x, int y) {
	}

}
