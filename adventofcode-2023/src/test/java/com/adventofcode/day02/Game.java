package com.adventofcode.day02;

import java.util.ArrayList;
import java.util.List;

public class Game {
	int id;

	List<CubesRevealed> rounds = new ArrayList<>();

	// tag::part1[]
	boolean isPossible(int maxRed, int maxBlue, int maxGreen) {
		return !rounds.stream().filter(round -> round.blue > maxBlue || round.red > maxRed || round.green > maxGreen)
				.findFirst().isPresent();
	}
	// end::part1[]

	CubesRevealed minimalSetOfCubes() {
		return rounds.stream().reduce(CubesRevealed::mapToMinimalSet).get();
	}

	/**
	 * This method will convert the String line of one game into a object oriented
	 * form.
	 *
	 * @param input String in form like "Game 3: 8 green, 6 blue, 20 red; 5 blue, 4
	 *              red, 13 green; 5 green, 1 red"
	 * @return a Game with the id and the rounds as list
	 */
	static Game makeGame(String input) {
		var game = new Game();

		var splitGameIdAndRounds = input.split(":");
		// retrieve the Id by replacing "Game "
		game.id = Integer.parseInt(splitGameIdAndRounds[0].replace("Game ", ""));

		// iterate of the list of round separated by ;
		for (String round : splitGameIdAndRounds[1].split(";")) {
			var revealed = new CubesRevealed();

			// split the rounds in single ones
			for (String cubesInRound : round.split(",")) {
				var cube = cubesInRound.strip().split(" ");
				var cubes = Integer.parseInt(cube[0].strip());
				if (cube[1].matches(".*green.*")) {
					revealed.green = cubes;
				} else if (cube[1].matches(".*blue.*")) {
					revealed.blue = cubes;
				} else if (cube[1].matches(".*red.*")) {
					revealed.red = cubes;
				}
			}
			game.rounds.add(revealed);
		}

		return game;
	}

	static class CubesRevealed {
		int green;
		int blue;
		int red;

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
			var cubesRevealed = new CubesRevealed();
			cubesRevealed.red = input1.red > input2.red ? input1.red : input2.red;
			cubesRevealed.green = input1.green > input2.green ? input1.green : input2.green;
			cubesRevealed.blue = input1.blue > input2.blue ? input1.blue : input2.blue;
			return cubesRevealed;
		}
		// end::part2[]
	}
}