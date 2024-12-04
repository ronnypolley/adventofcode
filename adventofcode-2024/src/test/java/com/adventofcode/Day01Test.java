package com.adventofcode;

import com.adventofcode.day01.TwoLists;
import com.adventofcode.junit.extension.AdventOfCodeDailySource;
import com.adventofcode.junit.util.AdventOfCodeAssertion;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.junit.jupiter.params.ParameterizedTest;

class Day01Test {

  @ParameterizedTest
  @AdventOfCodeDailySource
  void testPart1(Path fileName) throws IOException {
    TwoLists twoLists = TwoLists.fromFile(fileName);

    twoLists.list1.sort(Comparator.naturalOrder());
    twoLists.list2.sort(Comparator.naturalOrder());

    List<Long> resultList = new ArrayList<>();
    for (int i = 0; i < twoLists.list1.size(); i++) {
      resultList.add(Math.abs(twoLists.list1.get(i) - twoLists.list2.get(i)));
    }

    AdventOfCodeAssertion.assertAdventOfCode(fileName, 11L, resultList.stream().reduce(Long::sum).orElse(0L));
  }

  @ParameterizedTest
  @AdventOfCodeDailySource
  void testPart2(Path fileName) throws IOException {
    TwoLists twoLists = TwoLists.fromFile(fileName);
    AdventOfCodeAssertion.assertAdventOfCode(fileName, 31L,
        twoLists.list1.stream().mapToLong(value -> value * twoLists.list2.stream().filter(value2 -> value2.equals(value)).count())
            .reduce(Long::sum).orElse(0L));
  }
}
