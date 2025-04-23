package prob5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

public class Main {

  public static void main(String[] args) {
    List<Integer> list = Arrays.asList(0,0,3,8,4,11,13);
    System.out.println(secondSmallest(list));
  }

  public static <T extends Comparable<? super T>> T secondSmallest(List<T> list) {
    Objects.requireNonNull(list, "The input list cannot be null.");
    if (list.size() < 2) {
      throw new IllegalArgumentException("The list must contain at least two elements.");
    }

    List<T> sortedList = new ArrayList<>(list);
    Collections.sort(sortedList);

    T smallest = sortedList.get(0);
    for (int i = 1; i < sortedList.size(); i++) {
      if (sortedList.get(i).compareTo(smallest) > 0) {
        return sortedList.get(i);
      }
    }

    throw new IllegalArgumentException("No distinct second smallest element found.");
  }

}
