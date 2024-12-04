package com.adventofcode;

import com.adventofcode.junit.extension.AdventOfCodeDailySource;
import com.adventofcode.junit.util.AdventOfCodeAssertion;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;

class Day04Test {

  @ParameterizedTest
  @AdventOfCodeDailySource
  void testPart1(Path fileName) throws IOException {
    try (var lines = Files.lines(fileName)) {
      String[][] array = lines.map(line -> Arrays.stream(line.split("")).toArray(String[]::new)).toArray(String[][]::new);
      long result = 0;
      for (int i = 0; i < array.length; i++) {
        for (int j = 0; j < array[i].length; j++) {
          if ("X".equals(array[i][j])) {
            result += findXmas(array, i, j);
          }
        }
      }

      AdventOfCodeAssertion.assertAdventOfCode(fileName, 18L, result);
    }

  }

  private long findXmas(String[][] array, int i, int j) {
    return Stream.of(mapDiagonalLeftUp(array, i, j),
        mapUp(array, i, j),
        mapDiagRightUp(array, i, j),
        mapRight(array, i, j),
        mapDiagRightDown(array, i, j),
        mapDown(array, i, j),
        mapDiagLeftDown(array, i, j),
        mapLeft(array, i, j)).filter(string -> string.equals("XMAS")).count();
  }

  private String mapDiagLeftDown(String[][] array, int i, int j) {
    if (i+3 >= array.length  || j < 3 ) {
      return "";
    }

    return array[i][j].concat(array[i+1][j-1]).concat(array[i+2][j-2]).concat(array[i+3][j-3]);
  }

  private String mapDiagRightDown(String[][] array, int i, int j) {
    if (i+3 >= array.length  || j+3 >= array[i].length) {
      return "";
    }

    return array[i][j].concat(array[i+1][j+1]).concat(array[i+2][j+2]).concat(array[i+3][j+3]);
  }

  private String mapDiagRightUp(String[][] array, int i, int j) {
    if (i < 3  || j+3 >= array[i].length) {
      return "";
    }

    return array[i][j].concat(array[i-1][j+1]).concat(array[i-2][j+2]).concat(array[i-3][j+3]);
  }

  private String mapDiagonalLeftUp(String[][] array, int i, int j) {
    if (j < 3 || i < 3) {
      return "";
    }

    return array[i][j].concat(array[i-1][j-1]).concat(array[i-2][j-2]).concat(array[i-3][j-3]);
  }

  private String mapLeft(String[][] array, int i, int j) {
    if (j < 3) {
      return "";
    }

    return array[i][j].concat(array[i][j-1]).concat(array[i][j-2]).concat(array[i][j-3]);
  }

  private String mapDown(String[][] array, int i, int j) {
    if (i+3 >= array.length) {
      return "";
    }

    return array[i][j].concat(array[i+1][j]).concat(array[i+2][j]).concat(array[i+3][j]);
  }

  private String mapRight(String[][] array, int i, int j) {
    if (j+3 >= array[i].length) {
      return "";
    }

    return array[i][j].concat(array[i][j+1]).concat(array[i][j+2]).concat(array[i][j+3]);
  }

  private String mapUp(String[][] array, int i, int j) {
    if (i < 3) {
      return "";
    }
    return array[i][j].concat(array[i-1][j]).concat(array[i-2][j]).concat(array[i-3][j]);
  }

  @ParameterizedTest
  @AdventOfCodeDailySource
  void testPart2(Path fileName) throws IOException {
    try (var lines = Files.lines(fileName)) {
      String[][] array = lines.map(line -> Arrays.stream(line.split("")).toArray(String[]::new)).toArray(String[][]::new);
      long result = 0;
      for (int i = 0; i < array.length; i++) {
        for (int j = 0; j < array[i].length; j++) {
          if ("A".equals(array[i][j])) {
            result += findXmas2(array, i, j);
          }
        }
      }

      AdventOfCodeAssertion.assertAdventOfCode(fileName, 9L, result);
    }
  }

  private long findXmas2(String[][] array, int i, int j) {
    if (Stream.of(xmas(array, new int[]{i-1, i, i+1}, new int[]{j-1, j, j+1}),
        xmas(array, new int[]{i+1, i, i-1}, new int[]{j-1, j, j+1})).filter(string -> "MAS".equals(string) || "SAM".equals(string)).count() == 2) {
      return 1;
    }

    return 0;
  }


  private String xmas(String[][] array, int[] is, int[] js) {
    if (is[0] < 0 || js[0] < 0 || is[2] < 0 || is[0] >= array.length || is[2] >= array.length || js[2] >= array[is[0]].length) {
      return "";
    }
    return array[is[0]][js[0]].concat(array[is[1]][js[1]]).concat(array[is[2]][js[2]]);
  }

}
