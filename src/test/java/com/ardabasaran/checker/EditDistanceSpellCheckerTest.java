package com.ardabasaran.checker;

import org.junit.Before;
import org.junit.Test;

import java.net.URL;
import java.util.Map;
import java.util.SortedMap;

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
    SpellCheckerTestUtil.printTests(spellChecker);
  }
}
