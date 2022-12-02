package com.adventofcode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

class Day01Test {

	@Test
	void testPart1() throws IOException {
		Files.lines(Path.of("src/test/resources/day01input")).mapToInt(Integer::parseInt).reduce(this::findIncreasing);
		System.out.println(increases);
	}

	int increases = 0;
	
	private int findIncreasing(int pre_measure, int current_measure) {
		increases += pre_measure < current_measure ? 1 : 0;
		return current_measure;
	}
	
	@Test
	void testPart2() throws Exception {
		List<Integer> list = Files.lines(Path.of("src/test/resources/day01input")).mapToInt(Integer::parseInt).boxed().collect(Collectors.toList());
		for (int i = 0; i < list.size()-2; i++) {
			increases += list.stream().skip(i).limit(3).reduce(0, Integer::sum) < list.stream().skip(i+1).limit(3).reduce(0, Integer::sum) ? 1 : 0;  
		}
		System.out.println(increases);
	}
}