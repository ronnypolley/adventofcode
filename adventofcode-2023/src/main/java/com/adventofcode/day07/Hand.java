package com.adventofcode.day07;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.IntUnaryOperator;
import java.util.stream.IntStream;

class Hand implements Comparable<Hand> {

	Integer bid;
	String cards;

	Hand(String line) {
		var split = line.split(" ");
		bid = Integer.parseInt(split[1]);
		cards = split[0];
	}

	int getType() {
		// Five of a kind
		var count = cards.chars().distinct().count();
		if (count == 1) {
			return 7;
		}
		if (count == 2) {
			// 6 for Four of a kind and 5 for full house
			return countMostCommonCard(cards.chars()) == 4 ? 6 : 5;
		}
		if (count == 3) {
			// 3 for three of a kind and 2 for two pairs
			return countMostCommonCard(cards.chars()) == 3 ? 4 : 3;
		}
		// High card
		if (count == 4) {
			return 2;
		}
		return 1;
	}

	private Integer countMostCommonCard(IntStream hand) {
		Map<Integer, Integer> amountOfcard = new HashMap<>();
		hand.forEach(i -> {
			amountOfcard.computeIfAbsent(i, key -> 0);
			amountOfcard.put(i, amountOfcard.get(i) + 1);
		});
		return amountOfcard.entrySet().stream().reduce((e1, e2) -> e1.getValue() > e2.getValue() ? e1 : e2)
				.map(Entry::getValue).orElse(0);
	}

	@Override
	public int compareTo(Hand o) {
		var oType = o.getType();
		var mType = getType();

		if (oType != mType) {
			return mType - oType;
		}

		return compareChars(o);
	}

	protected int compareChars(Hand o) {
		var mapO = o.cards.chars().map(mapCardsToInts()).toArray();
		var mapM = cards.chars().map(mapCardsToInts()).toArray();

		for (var i = 0; i < mapM.length; i++) {
			if (mapO[i] == mapM[i]) {
				continue;
			}
			return mapM[i] - mapO[i];
		}
		return 0;
	}

	protected IntUnaryOperator mapCardsToInts() {
		return i -> switch (i) {
		case 65 -> 57 + 10;
		case 75 -> 57 + 9;
		case 81 -> 57 + 8;
		case 74 -> 57 + 7;
		case 84 -> 57 + 6;
		default -> i;
		};
	}

	@Override
	public int hashCode() {
		return Objects.hash(bid, cards);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if ((obj == null) || (getClass() != obj.getClass())) {
			return false;
		}
		var other = (Hand) obj;
		return Objects.equals(bid, other.bid) && Objects.equals(cards, other.cards);
	}

}