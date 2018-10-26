package com.ardabasaran.checker;

import org.junit.Before;
import org.junit.Test;

import java.net.URL;

public class EditDistanceSpellCheckerTest {
  EditDistanceSpellChecker spellChecker;

  @Before
  public void setUp() throws Exception {
    URL small = DefaultSpellCheckerTest.class.getClassLoader().getResource("small.txt");
    assert small != null;
    spellChecker = (EditDistanceSpellChecker) SpellCheckerFactory.createEditDistanceSpellChecker(small);
  }

  @Test
  public void check() {

  }

}