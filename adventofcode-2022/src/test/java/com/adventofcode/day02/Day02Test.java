package com.adventofcode.day02;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class Day02Test {
	
	static String fileName = "src/test/resources/day02input";

	@ParameterizedTest
	@CsvSource(textBlock = """
			#X, Y, Z
			 1, 2, 3
			 1, 3, 2
			 2, 1, 3
			 2, 3, 1
			 3, 2, 1
			 3, 1, 2
			""")
	void testPart1(int valueX, int valueY, int valueZ) throws IOException {
		System.out.println(Files.lines(Path.of(fileName)).map(str -> {
			String[] split = str.split(" ");
			int op = "A".equals(split[0]) ? 1 : "B".equals(split[0]) ? 2 : 3;
			int valueMe = "X".equals(split[1]) ? valueX : "Y".equals(split[1]) ? valueY : valueZ;
			switch(op - valueMe) {
			case 0: return 3 + valueMe;
				// rock (1) & paper (2)
				// paper(2) & scissor (3)
			case -1:
				return 6 + valueMe;
			case -2:
				// rock (1) & scissor (3)
				return 0 + valueMe;
			case 2:
				//  scissor (3) & rock (1)
				return 6 + valueMe;
			case 1: 
				// scissor (3) & paper(2)
				// paper (2) & rock(1)
				return 0 + valueMe;
			default: return Integer.MAX_VALUE;
			}
		}).reduce(0, Integer::sum));
	}
	
	@Test
	void testPart2() throws Exception {
		System.out.println(Files.lines(Path.of(fileName)).map(str -> {
			String[] split = str.split(" ");
			int op = "A".equals(split[0]) ? 1 : "B".equals(split[0]) ? 2 : 3;
			switch(split[1]) {
			case "X": return op == 1 ? 3 : op == 2 ? 1 : 2;
			case "Y": return 3 + (op == 1 ? 1 : op == 2 ? 2 : 3);
			case "Z": return 6 + (op == 1 ? 2 : op == 2 ? 3 : 1);
			default: return Integer.MAX_VALUE;
			}
		}).reduce(0, Integer::sum));
	}
}
