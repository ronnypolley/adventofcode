package com.adventofcode.day05;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;

import com.adventofcode.junit.extension.AdventOfCodeDailySource;
import com.adventofcode.junit.util.AdventOfCodeAssertion;

class Day05Test {

	@ParameterizedTest
	@AdventOfCodeDailySource
	void testPart1(Path file) throws IOException {
		var lines = Files.readAllLines(file);
		var seeds = prepareSeedsPart1(lines);
		var mapper = createMappers(lines);

		AdventOfCodeAssertion.assertAdventOfCode(file, 35L, computeLowestLocation(mapper, seeds));
	}

	@ParameterizedTest
	@AdventOfCodeDailySource
	@Disabled("till it is faster")
	void testPart2(Path file) throws IOException {
		var lines = Files.readAllLines(file);
		var seeds = prepareSeedsPart2(lines);
		var mapper = createMappers(lines);

		AdventOfCodeAssertion.assertAdventOfCode(file, 46L, computeLowestLocation2(mapper, seeds));
	}

	private List<Seed> prepareSeedsPart1(List<String> lines) {
		var seeds = Arrays.stream(lines.getFirst().replace("seeds: ", "").split(" ")).map(Long::parseLong).map(Seed::new)
				.toList();

		lines.removeFirst();
		return seeds;
	}

	private List<Seed2> prepareSeedsPart2(List<String> lines) {
		var split = lines.getFirst().replace("seeds: ", "").split(" ");
		var seeds = new ArrayList<Seed2>();
		for (var i = 0; i < split.length; i += 2) {
			seeds.add(new Seed2(Long.parseLong(split[i]), Long.parseLong(split[i + 1])));
		}

		lines.removeFirst();
		return seeds;
	}

	private Map<MapperEnum, Mapper> createMappers(List<String> lines) {
		Map<MapperEnum, Mapper> mapper = new HashMap<>();
		Mapper current = null;

		for (String line : lines) {
			if (line.isBlank()) {
				continue;
			}
			if (line.contains("map:")) {
				current = new Mapper(MapperEnum.findByKey(line));
				mapper.put(current.id, current);
				continue;
			}

			current.addLine(line);
		}
		return mapper;
	}

	private long computeLowestLocation(Map<MapperEnum, Mapper> mapper, List<Seed> seeds) {
		return seeds.stream().mapToLong(s -> mapper.get(MapperEnum.HUMIDITY_2_LOCATION)
				.computeNumber(mapper.get(MapperEnum.TEMP_2_HUMIDITY).computeNumber(mapper.get(MapperEnum.LIGHT_2_TEMP)
						.computeNumber(mapper.get(MapperEnum.WATER_2_LIGHT)
								.computeNumber(mapper.get(MapperEnum.FERTILIZER_2_WATER)
										.computeNumber(mapper.get(MapperEnum.SOIL_TO_FERTILIZER).computeNumber(
												mapper.get(MapperEnum.SEED_2_SOIL).computeNumber(s.seedNumber))))))))
				.min().orElseThrow();
	}

	private long computeLowestLocation2(Map<MapperEnum, Mapper> mapper, List<Seed2> seeds) {

		for (Seed2 seed2 : seeds) {
			var seedMin = Long.MAX_VALUE;
			for (var i = seed2.seedNumber; i < seed2.seedNumber + seed2.length; i++) {
				var location = mapper.get(MapperEnum.HUMIDITY_2_LOCATION)
						.computeNumber(mapper.get(MapperEnum.TEMP_2_HUMIDITY)
								.computeNumber(mapper.get(MapperEnum.LIGHT_2_TEMP)
										.computeNumber(mapper.get(MapperEnum.WATER_2_LIGHT)
												.computeNumber(mapper.get(MapperEnum.FERTILIZER_2_WATER).computeNumber(
														mapper.get(MapperEnum.SOIL_TO_FERTILIZER).computeNumber(
																mapper.get(MapperEnum.SEED_2_SOIL).computeNumber(i)))))));
				if (seedMin > location) {
					seedMin = location;
				}
			}
			seed2.locationNumber = seedMin;
		}
		return seeds.stream().mapToLong(s -> s.locationNumber).min().orElseThrow();
	}

	static class Seed {
		long seedNumber;

		public Seed(long i) {
			seedNumber = i;
		}
	}

	static class Seed2 {
		long seedNumber;
		long length;
		long locationNumber;

		public Seed2(long i, long length) {
			seedNumber = i;
			this.length = length;
		}
	}

	enum MapperEnum {
		SEED_2_SOIL("seed-to-soil"), SOIL_TO_FERTILIZER("soil-to-fertilizer"), FERTILIZER_2_WATER("fertilizer-to-water"),
		WATER_2_LIGHT("water-to-light"), LIGHT_2_TEMP("light-to-temperature"), TEMP_2_HUMIDITY("temperature-to-humidity"),
		HUMIDITY_2_LOCATION("humidity-to-location");

		final String key;

		MapperEnum(String string) {
			this.key = string;
		}

		static MapperEnum findByKey(String key) {
			return Arrays.stream(values()).filter(s -> key.contains(s.key)).findFirst().orElseThrow();
		}
	}

	static class Mapper {
		public Mapper(MapperEnum findByKey) {
			id = findByKey;
		}

		public void addLine(String line) {
			mappingList.add(new Source2Dest(line.split(" ")));
		}

		MapperEnum id;

		List<Source2Dest> mappingList = new ArrayList<>();

		long computeNumber(long seedNumber) {
			long computeNumber;
			for (Source2Dest source2Dest : mappingList) {
				computeNumber = source2Dest.computeNumber(seedNumber);
				if (computeNumber >= 0L) {
					return computeNumber;
				}
			}
			return seedNumber;
		}

		static class Source2Dest {
			long source;
			long dest;
			long length;

			public Source2Dest(String[] split) {
				var is = Arrays.stream(split).mapToLong(Long::parseLong).toArray();
				source = is[1];
				dest = is[0];
				length = is[2];
			}

			long computeNumber(long seedNumber) {
				var index = seedNumber - source;
				if (index >= 0 && index < length) {
					return dest + index;
				}
				return -1;
			}
		}

	}

}
