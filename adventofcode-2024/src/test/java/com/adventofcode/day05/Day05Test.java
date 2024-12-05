package com.adventofcode.day05;

import com.adventofcode.junit.extension.AdventOfCodeDailySource;
import com.adventofcode.junit.util.AdventOfCodeAssertion;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.params.ParameterizedTest;

class Day05Test {

  List<BookPageOrder> orders = new ArrayList<>();
  List<BookUpdate> updates = new ArrayList<>();

  @ParameterizedTest
  @AdventOfCodeDailySource
  void testPart1(Path fileName) throws IOException {
    loadData(fileName);

    AdventOfCodeAssertion.assertAdventOfCode(fileName, 143, updates.stream().filter(u -> u.applyRules(orders)).mapToInt(BookUpdate::getMiddlePageNumber).sum());
  }

  @ParameterizedTest
  @AdventOfCodeDailySource
  void testPart2(Path fileName) throws IOException {
    loadData(fileName);

    AdventOfCodeAssertion.assertAdventOfCode(fileName, 123, updates.stream().filter(u -> !u.applyRules(orders)).map(u -> u.reorder(orders)).mapToInt(BookUpdate::getMiddlePageNumber).sum());
  }

  private void loadData(Path fileName) throws IOException {
    try (var lines = Files.lines(fileName)) {
      lines.forEach(line -> {
        if (line.contains("|")) {
          orders.add(BookPageOrder.buildFromInput(line));
        }
        if (line.contains(",")) {
          updates.add(BookUpdate.fromInput(line));
        }
      });
    }
  }
}
