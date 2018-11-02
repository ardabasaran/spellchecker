package com.ardabasaran.checker;

import org.junit.Before;
import org.junit.Test;

import java.net.URL;
import java.util.*;

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
    SpellCheckerTestUtil.printTests(spellChecker);
  }
}