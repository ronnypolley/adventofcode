package com.adventofcode.day01;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class TwoLists {

  List<Long> list1;
  List<Long> list2;

  private TwoLists(List<Long> list1, List<Long> list2) {
    this.list1 = list1;
    this.list2 = list2;
  }


  static TwoLists fromFile(Path fileName) throws IOException {
    List<Long> list1 = new ArrayList<>();
    List<Long> list2 = new ArrayList<>();
    Files.readAllLines(fileName).forEach(line -> {
      String[] locations = line.split("\\s+");
      list1.add(Long.parseLong(locations[0]));
      list2.add(Long.parseLong(locations[1]));
    });

    return new TwoLists(list1, list2);
  }
}
