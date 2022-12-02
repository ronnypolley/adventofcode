package com.adventofcode;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class Day02Test {

	@Test
	void testPart1() throws IOException {
		Position position = Files.lines(Paths.get("src/test/resources/day02input")).map( str -> {
			String[] split = str.split(" ");
			return switch (split[0]) {
				case "forward" -> new Position(0, Integer.parseInt(split[1]));
				case "down" -> new Position(Integer.parseInt(split[1]), 0);
				case "up" -> new Position(-Integer.parseInt(split[1]), 0);
				default -> new Position(0, 0);
			};
			
		}).reduce(new Position(0,0), this::computePosition);
		System.out.println(position.depth * position.horizontal);
	}
	
	record Position(int depth, int horizontal) {}

	private Position computePosition(Position position1, Position position2) {
		return new Position(position1.depth + position2.depth, position1.horizontal+position2.horizontal);
	};
	
	@ParameterizedTest
	@ValueSource(strings = {"day02input_test", "day02input"})
	void testPart2(String filename) throws Exception {
		Position2 position = Files.lines(Paths.get("src/test/resources/"+filename)).map( str -> {
			String[] split = str.split(" ");
			return switch (split[0]) {
				case "forward" -> new Position2(0, Integer.parseInt(split[1]),0);
				case "down" -> new Position2(0, 0,Integer.parseInt(split[1]));
				case "up" -> new Position2(0, 0,-Integer.parseInt(split[1]));
				default -> new Position2(0, 0, 0);
			};
			
		}).reduce(new Position2(0,0,0), this::computePosition2);
		
		if (filename.endsWith("test")) {
			assertEquals(900, position.depth*position.horizontal);
		} else {
			System.out.println(position.depth * position.horizontal);			
		}
	}
	
	record Position2(int depth, int horizontal, int aim) {}

	private Position2 computePosition2(Position2 position21, Position2 position22) {
		return new Position2(position21.depth+(position21.aim*position22.horizontal), position21.horizontal+position22.horizontal, position21.aim+position22.aim);
	}

}
