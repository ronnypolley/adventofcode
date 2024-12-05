package com.adventofcode.day05;

public class BookPageOrder {

  int before;

  int after;

  private BookPageOrder(int before, int after) {
    this.before = before;
    this.after = after;
  }

  public static BookPageOrder buildFromInput(String input) {
    String[] split = input.split("\\|");
    return new BookPageOrder(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
  }

  public int getBefore() {
    return before;
  }

  public int getAfter() {
    return after;
  }
}
