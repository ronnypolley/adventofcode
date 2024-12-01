package com.adventofcode.day01;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class TwoLists {

  List<Long> list1 = new ArrayList<>();
  List<Long> list2 = new ArrayList<>();

  public TwoLists(Path fileName) throws IOException {
    Files.readAllLines(fileName).forEach(line -> {
      String[] locations = line.split("\\s+");
      list1.add(Long.parseLong(locations[0]));
      list2.add(Long.parseLong(locations[1]));
    });
  }
}
