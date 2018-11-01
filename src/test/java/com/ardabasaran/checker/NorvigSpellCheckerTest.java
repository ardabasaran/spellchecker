package com.ardabasaran.checker;

import org.junit.Before;
import org.junit.Test;

import java.net.URL;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.*;

public class NorvigSpellCheckerTest {

  NorvigSpellChecker spellChecker;

  @Before
  public void setUp() throws Exception {
    URL small = NorvigSpellCheckerTest.class.getClassLoader().getResource("small.txt");
    assert small != null;
    spellChecker = (NorvigSpellChecker) SpellCheckerFactory.createNorvigSpellChecker(small);
  }

  @Test
  public void check() {
    String[] testWords = {"oen", "two", "thee", "ne", "a"};
    for (String testWord : testWords) {
      Map<Double, String> corrections = new TreeMap<>(Collections.reverseOrder());
      Map<Double, String> response = spellChecker.check(testWord)
          .getCorrectionsByScore();
      for (Map.Entry<Double, String> entry: response.entrySet()) {
        corrections.put(entry.getKey(), entry.getValue());
      }
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