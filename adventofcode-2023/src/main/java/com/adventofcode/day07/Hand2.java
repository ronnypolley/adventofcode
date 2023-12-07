package com.adventofcode.day07;

import java.util.List;
import java.util.function.IntUnaryOperator;

class Hand2 extends Hand {

	Hand2(String line) {
		super(line);
	}

	@Override
	public int compareTo(Hand o) {
		var typeO = o.getType();
		var typeM = getType();
		for (String replacement : List.of("A", "Q", "T", "K", "9", "8", "7", "6", "5", "4", "3", "2", "1")) {
			var type = new Hand(o.cards.replace("J", replacement) + " " + o.bid).getType();
			typeO = typeO > type ? typeO : type;

			type = new Hand(cards.replace("J", replacement) + " " + bid).getType();
			typeM = typeM > type ? typeM : type;
		}

		if (typeO != typeM) {
			return typeM - typeO;
		}

		return compareChars(o);
	}

	@Override
	protected IntUnaryOperator mapCardsToInts() {
		return i -> switch (i) {
		case 74 -> 10;
		default -> super.mapCardsToInts().applyAsInt(i);
		};
	}

}