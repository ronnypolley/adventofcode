package com.adventofcode.day05;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class BookUpdate {

  List<Integer> updates;

  private BookUpdate(List<Integer> updates) {
    this.updates = updates;
  }

  public static BookUpdate fromInput(String input) {
    return new BookUpdate(Arrays.stream(input.split(",")).mapToInt(Integer::parseInt).boxed().toList());
  }

  public boolean applyRules(List<BookPageOrder> rules) {
    return rules.stream().allMatch(rule -> updates.indexOf(rule.getBefore()) <= updates.indexOf(rule.getAfter()) ||
        !updates.contains(rule.getAfter()));
  }

  public int getMiddlePageNumber() {
    return updates.get(updates.size()/2);
  }

  public BookUpdate reorder(List<BookPageOrder> orders) {
    ArrayList<Integer> list = new ArrayList<>(updates);

    list.sort((i1, i2) -> {
      // For the reordering, we only need the rules, where both values are included.
      // All other cases does not impact the sorting of these two values.
      Optional<BookPageOrder> rule =
          orders.stream()
              .filter(o -> (o.before == i1 && o.after == i2) || (o.before == i2 && o.after == i1))
              .findFirst();
      return rule.map(bookPageOrder -> bookPageOrder.before == i1 ? -1 : 1).orElse(0);
    });

    return new BookUpdate(list);
  }

}
