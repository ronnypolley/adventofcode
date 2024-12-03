package com.adventofcode.day01;

import com.adventofcode.junit.extension.AdventOfCodeDailySource;
import com.adventofcode.junit.util.AdventOfCodeAssertion;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.jupiter.params.ParameterizedTest;

class Day03Test {

  Pattern number = Pattern.compile(".*\\((\\d{1,3}),(\\d{1,3})\\).*");

  @ParameterizedTest
  @AdventOfCodeDailySource
  void testPart1(Path fileName) throws IOException {
    Pattern pattern = Pattern.compile("(mul\\(\\d+,\\d+\\))");
    long strings = Files.readAllLines(fileName).stream().map(string -> extractCorrectInstructions(string, pattern)).flatMap(Collection::stream).mapToLong(this::multiply).sum();

    AdventOfCodeAssertion.assertAdventOfCode(fileName, 161L, strings);
  }

  private long multiply(String string) {
    Matcher matcher = number.matcher(string);
    if (matcher.matches()) {
      return Long.parseLong(matcher.group(1)) * Long.parseLong(matcher.group(2));
    }
    return 0;
  }


  private long multiplyIfNeeded(boolean compute, String s) {
    if (!compute) {
      return 0;
    }
    return multiply(s);
  }

  @ParameterizedTest
  @AdventOfCodeDailySource
  void testPart2(Path fileName) throws IOException {
    Pattern pattern = Pattern.compile("(mul\\(\\d+,\\d+\\))|(do\\(\\))|(don't\\(\\))");

    final AtomicBoolean compute = new AtomicBoolean(true);

    long sum = Files.readAllLines(fileName).stream().map(string -> extractCorrectInstructions(string, pattern)).flatMap(Collection::stream).mapToLong(s -> processItem(s, compute)).sum();

    AdventOfCodeAssertion.assertAdventOfCode(fileName, 48L, sum);
  }

  private static ArrayList<String> extractCorrectInstructions(String string, Pattern pattern) {
    ArrayList<String> list = new ArrayList<>();
    Matcher matcher = pattern.matcher(string);
    while (matcher.find()) {
      list.add(matcher.group());
    }
    return list;
  }

  private long processItem(String s, AtomicBoolean compute) {
    return switch (s) {
      case "don't()" -> {
        compute.set(false);
        yield 0;
      }
      case "do()" -> {
        compute.set(true);
        yield 0;
      }
      default -> multiplyIfNeeded(compute.get(), s);
    };
  }

}
