package com.ardabasaran.checker;

import org.junit.Before;
import org.junit.Test;

import java.net.URL;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class DefaultSpellCheckerTest {
  SpellChecker spellChecker;

  @Before
  public void setUp() throws Exception {
    URL small = DefaultSpellCheckerTest.class.getClassLoader().getResource("small.txt");
    assert small != null;
    spellChecker = SpellCheckerFactory.createDefaultSpellChecker(small);
  }

  @Test
  public void check() {
    assertEquals(0, spellChecker.check("one").getCorrectionsByScore().size());
    assertEquals(0, spellChecker.check("two").getCorrectionsByScore().size());
    assertEquals(0, spellChecker.check("NINE").getCorrectionsByScore().size());
  }

  @Test
  public void inverFrequencyMap() {
    System.out.println("Inverse Frequency");
    for (Map.Entry<String, Double> entry : spellChecker.getWordsByInverseFrequency().entrySet()) {
      System.out.println(entry.getKey() + " : " + entry.getValue());
    }
  }

  @Test
  public void probabilityMap() {
    System.out.println("Probability");
    for (Map.Entry<String, Double> entry : spellChecker.getWordsByProbability().entrySet()) {
      System.out.println(entry.getKey() + " : " + entry.getValue());
    }
  }
}