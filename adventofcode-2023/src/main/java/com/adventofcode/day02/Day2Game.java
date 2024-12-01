package com.adventofcode.day02;

import java.util.ArrayList;
import java.util.List;

public record Day2Game(int id, List<CubesRevealed> rounds) {

	// tag::part1[]
	boolean isPossible(int maxRed, int maxBlue, int maxGreen) {
		return rounds.stream().noneMatch(round -> round.blue > maxBlue || round.red > maxRed || round.green > maxGreen);
	}
	// end::part1[]

	CubesRevealed minimalSetOfCubes() {
		return rounds.stream().reduce(CubesRevealed::mapToMinimalSet).orElse(null);
	}

	/**
	 * This method will convert the String line of one game into a object oriented
	 * form.
	 *
	 * @param input String in form like "Game 3: 8 green, 6 blue, 20 red; 5 blue, 4
	 *              red, 13 green; 5 green, 1 red"
	 * @return a Game with the id and the rounds as list
	 */
	static Day2Game makeGame(String input) {

		var splitGameIdAndRounds = input.split(":");
		// retrieve the Id by replacing "Game "
		var game = new Day2Game(Integer.parseInt(splitGameIdAndRounds[0].replace("Game ", "")), new ArrayList<>());

		// iterate of the list of round separated by ";"
		for (String round : splitGameIdAndRounds[1].split(";")) {
			var green = 0;
			var red = 0;
			var blue = 0;

			// split the rounds in single ones
			for (String cubesInRound : round.split(",")) {
				var cube = cubesInRound.strip().split(" ");
				var cubes = Integer.parseInt(cube[0].strip());
				if (cube[1].matches(".*green.*")) {
					green = cubes;
				} else if (cube[1].matches(".*blue.*")) {
					blue = cubes;
				} else if (cube[1].matches(".*red.*")) {
					red = cubes;
				}
			}
			game.rounds().add(new CubesRevealed(green, blue, red));
		}

		return game;
	}

	static record CubesRevealed(int green, int blue, int red) {

		/**
		 * Map function used in a stream to find the minimal cubes viable for a game by
		 * reducing two input to one.
		 *
		 * @param input1
		 * @param input2
		 * @return
		 */
		// tag::part2[]
		static CubesRevealed mapToMinimalSet(CubesRevealed input1, CubesRevealed input2) {
			return new CubesRevealed(input1.green > input2.green ? input1.green : input2.green,
					input1.blue > input2.blue ? input1.blue : input2.blue,
					input1.red > input2.red ? input1.red : input2.red);
		}
		// end::part2[]
	}
}