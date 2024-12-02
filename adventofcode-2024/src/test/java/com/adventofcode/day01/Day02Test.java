package com.adventofcode.day01;

import com.adventofcode.junit.extension.AdventOfCodeDailySource;
import com.adventofcode.junit.util.AdventOfCodeAssertion;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;

class Day02Test {

  @ParameterizedTest
  @AdventOfCodeDailySource
  void testPart1(Path fileName) throws IOException {
    try (var lines = Files.lines(fileName)){
      AdventOfCodeAssertion.assertAdventOfCode(fileName, 2L, mapToIntList(lines).filter(list ->
        (list.equals(list.stream().sorted().toList()) || list.equals(list.stream().sorted().toList().reversed())) && distantCheck(list)
      ).count());
    }
  }

  private static Stream<List<Integer>> mapToIntList(Stream<String> lines) {
    return lines.map(line -> Arrays.stream(line.split(" ")).mapToInt(Integer::parseInt).boxed().toList());
  }

  private boolean distantCheck(List<Integer> list) {
    return list.stream().reduce((n, m) -> (Math.abs(n-m) <= 3 && Math.abs(n-m) > 0)? m : Integer.MAX_VALUE).filter(n -> n == Integer.MAX_VALUE).isEmpty();
  }

  @ParameterizedTest
  @AdventOfCodeDailySource
  void testPart2(Path fileName) throws IOException {
    try (var lines = Files.lines(fileName)){
      AdventOfCodeAssertion.assertAdventOfCode(fileName, 4L, mapToIntList(lines).filter(this::lineCheckWithOneErrorAllowed).count());
    }
  }

  private boolean lineCheckWithOneErrorAllowed(List<Integer> integers) {
    return IntStream.range(0, integers.size()).mapToObj(i -> {
      ArrayList<Integer> list = new ArrayList<>(integers);
      list.remove(i);
      return (list.equals(list.stream().sorted().toList()) || list.equals(list.stream().sorted().toList().reversed())) && distantCheck(list);
    }).anyMatch(b -> b);
  }

}
