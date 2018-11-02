package com.ardabasaran.checker;

import java.util.Map;
import java.util.SortedMap;

public class SpellCheckerTestUtil {
  static void printTests(SpellChecker spellChecker) {
    String[] testWords = {"oen", "two", "thee", "ne", "a"};
    for (String testWord : testWords) {
      SortedMap<Double, String> corrections = spellChecker.check(testWord)
          .getCorrectionsByScore();

      System.out.println("-------------------");
      System.out.println("Test word: " + testWord);
      System.out.println("-------------------");
      for (Map.Entry<Double, String> entry : corrections.entrySet()) {
        System.out.println(entry.getValue() + ", " + entry.getKey());
      }
    }
  }
}
