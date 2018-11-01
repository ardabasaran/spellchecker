package com.ardabasaran.checker;

import org.junit.Before;
import org.junit.Test;

import java.net.URL;
import java.util.Map;
import java.util.TreeMap;

import static junit.framework.TestCase.assertTrue;

public class EditDistanceSpellCheckerTest {
  EditDistanceSpellChecker spellChecker;

  @Before
  public void setUp() throws Exception {
    URL small = EditDistanceSpellCheckerTest.class.getClassLoader().getResource("small.txt");
    assert small != null;
    spellChecker = (EditDistanceSpellChecker) SpellCheckerFactory.createEditDistanceSpellChecker(small);
  }

  @Test
  public void check() {
    SpellCheckResponse response;
    String[] testWords = {"oen", "two", "thee", "ne", "a"};
    for (String testWord : testWords) {
      Map<Double, String> corrections = new TreeMap<>(spellChecker.check(testWord)
          .getCorrectionsByScore());
      assertTrue(corrections.size()>0);

      System.out.println("-------------------");
      System.out.println("Test word: " + testWord);
      System.out.println("-------------------");
      for (Map.Entry<Double, String> entry : corrections.entrySet()) {
        System.out.println(entry.getValue() + ", " + entry.getKey());
      }
    }
  }

}
