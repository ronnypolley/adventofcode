package com.adventofcode.day13;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.params.ParameterizedTest;

import com.adventofcode.junit.extension.AdventOfCodeDailySource;
import com.adventofcode.junit.util.AdventOfCodeAssertion;

class Day13Test {

	Logger logger = LogManager.getLogger();

	@ParameterizedTest
	@AdventOfCodeDailySource
	void testPart1(Path file) throws IOException {
		List<PacketPair> pairs = prepareInput(file);
		AdventOfCodeAssertion.assertAdventOfCode(file, 13,
				pairs.stream().filter(pair -> pair.list1.compareTo(pair.list2) <= 0).mapToInt(pair -> pair.id).sum());
	}

	@ParameterizedTest
	@AdventOfCodeDailySource
	void testPart2(Path file) throws IOException {
		List<ListPacket> input = prepareInput(file).stream().flatMap(pair -> Stream.of(pair.list1, pair.list2))
				.collect(Collectors.toCollection(ArrayList::new));
		ListPacket divider1 = new ListPacket("[2]");
		ListPacket divider2 = new ListPacket("[6]");

		input.addAll(List.of(divider1, divider2));
		input.sort(Packet::compareTo);

		AdventOfCodeAssertion.assertAdventOfCode(file, 140,
				(input.indexOf(divider1) + 1) * (input.indexOf(divider2) + 1));
	}

	private List<PacketPair> prepareInput(Path file) throws IOException {
		List<String> readAllLines = Files.readAllLines(file);
		List<PacketPair> pairs = new ArrayList<Day13Test.PacketPair>();
		int id = 1;
		for (int i = 0; i < readAllLines.size(); i += 3) {
			var list1 = new ListPacket(readAllLines.get(i).substring(1, readAllLines.get(i).length() - 1));
			var list2 = new ListPacket(readAllLines.get(i + 1).substring(1, readAllLines.get(i + 1).length() - 1));
			pairs.add(new PacketPair(id++, list1, list2));
		}
		return pairs;
	}

	private class PacketPair {
		int id;

		ListPacket list1;
		ListPacket list2;

		public PacketPair(int id, ListPacket list1, ListPacket list2) {
			this.id = id;
			this.list1 = list1;
			this.list2 = list2;
		}

		@Override
		public String toString() {
			return "" + id + ": " + list1 + "; " + list2;
		}
	}

	private abstract class Packet implements Comparable<Packet> {
	}

	private class IntegerPacket extends Packet {

		int value;

		public IntegerPacket(String input) {
			value = Integer.parseInt(input.strip());
		}

		@Override
		public int compareTo(Packet o) {
			if (o instanceof IntegerPacket integer) {
				return this.value - integer.value;
			}

			if (o instanceof ListPacket incomingList) {
				return new ListPacket(this).compareTo(incomingList);
			}

			return 0;
		}

		@Override
		public String toString() {
			return String.valueOf(value);
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getEnclosingInstance().hashCode();
			result = prime * result + Objects.hash(value);
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			IntegerPacket other = (IntegerPacket) obj;
			if (!getEnclosingInstance().equals(other.getEnclosingInstance()))
				return false;
			return value == other.value;
		}

		private Day13Test getEnclosingInstance() {
			return Day13Test.this;
		}

	}

	private class ListPacket extends Packet {

		List<Packet> list = new ArrayList<>();

		public ListPacket(String packet) {
			while (!packet.isBlank()) {
				if (packet.charAt(0) == ',') {
					packet = packet.substring(1);
				}
				if (packet.charAt(0) == '[') {
					var endIndex = 1;
					var found = 1;
					while (endIndex < packet.length() && found > 0) {
						if (packet.charAt(endIndex) == ']') {
							found--;
						} else if (packet.charAt(endIndex) == '[') {
							found++;
						}
						endIndex++;
					}
					list.add(new ListPacket(packet.substring(1, endIndex - 1)));
					packet = packet.substring(endIndex);
				} else {
					int endIndex = 1;
					while (endIndex < packet.length() && Character.isDigit(packet.charAt(endIndex))) {
						endIndex++;
					}
					list.add(new IntegerPacket(packet.substring(0, endIndex)));
					packet = packet.length() == endIndex ? "" : packet.substring(endIndex);
				}
			}
		}

		public ListPacket(IntegerPacket packet) {
			list.add(packet);
		}

		@Override
		public int compareTo(Packet o) {
			ListPacket toCompare = o instanceof IntegerPacket incomingInteger ? new ListPacket(incomingInteger)
					: (ListPacket) o;

			int compareTo = 0;
			for (int i = 0; i < this.list.size() && i < toCompare.list.size() && compareTo == 0; i++) {
				compareTo = this.list.get(i).compareTo(toCompare.list.get(i));
			}

			return compareTo != 0 ? compareTo : this.list.size() - toCompare.list.size();
		}

		@Override
		public String toString() {
			return "" + list;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getEnclosingInstance().hashCode();
			result = prime * result + Objects.hash(list);
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			ListPacket other = (ListPacket) obj;
			if (!getEnclosingInstance().equals(other.getEnclosingInstance()))
				return false;
			return Objects.equals(list, other.list);
		}

		private Day13Test getEnclosingInstance() {
			return Day13Test.this;
		}

	}
}
