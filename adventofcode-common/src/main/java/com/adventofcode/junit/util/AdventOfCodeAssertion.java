package com.adventofcode.junit.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;

public class AdventOfCodeAssertion {

	public static void assertAdventOfCode(Path file, Integer expected, Integer actual) {
		if (file.toString().endsWith("test")) {
			assertEquals(expected, actual);
		} else {
			System.out.println(actual);
		}
	}

}
