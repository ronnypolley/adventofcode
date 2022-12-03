package com.adventofcode.day01;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class Day01Test {

	@ParameterizedTest
	@ValueSource(strings = {"day01input_test", "day01input"})
	void testPart1(String filename) throws IOException {
		Path path = Paths.get("src/test/resources/"+filename);

		int currentCals = 0;
		int maxCal = 0;
		for(String cal : Files.lines(path).collect(Collectors.toList())) {
			if (cal.isBlank()) {
				if (maxCal < currentCals) {
					maxCal = currentCals;
				}
				currentCals = 0;
			} else {
				currentCals += Integer.parseInt(cal);
			}
		}
		
		if (filename.endsWith("test")) {
			assertEquals(24000, maxCal);
		}
		
		System.out.println(maxCal);
	}
	
	@Test
	void testPart2() throws Exception {
		Path path = Paths.get("src/test/resources/day01input");
		
		List<Integer> elvesCals = new ArrayList<>();
		int currentCals = 0;
		for(String cal : Files.lines(path).collect(Collectors.toList())) {
			if (cal.isBlank()) {
				elvesCals.add(currentCals);
				currentCals = 0;
			} else {
				currentCals += Integer.parseInt(cal);
			}
		}
		System.out.println(elvesCals);
		System.out.println(elvesCals.stream().sorted(Comparator.reverseOrder()).limit(3).reduce(0, Integer::sum));
	}

}
