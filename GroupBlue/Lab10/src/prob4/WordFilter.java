package prob4;

import java.util.List;

public class WordFilter {
  public static void main(final String[] args) {
    WordFilter wf = new WordFilter();
    List<String> words = List.of("cd", "abce", "acdef", "dddd", "qsdf", "cb", "azerty");
    int result = wf.countWords(words, 'c', 'd', 4);
    System.out.println("Count: " + result);
  }

  public int countWords(List<String> words, char c, char d, int len) {
    return (int) words.stream()
        .filter(word -> hasLength(word, len))
        .filter(word -> containsChar(word, c))
        .filter(word -> notContainsChar(word, d))
        .count();
  }

  private boolean hasLength(String word, int len) {
    return word.length() == len;
  }

  private boolean containsChar(String word, char c) {
    return word.indexOf(c) >= 0;
  }

  private boolean notContainsChar(String word, char d) {
    return word.indexOf(d) == -1;
  }
}
