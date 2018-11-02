package com.ardabasaran.checker;

import org.junit.Before;
import org.junit.Test;

import java.net.URL;
import java.util.Map;
import java.util.SortedSet;

import static org.junit.Assert.assertTrue;

public class SymSpellCheckerTest {
  SymSpellChecker spellChecker;

  @Before
  public void setUp() throws Exception {
    URL small = SymSpellCheckerTest.class.getClassLoader().getResource("small.txt");
    assert small != null;
    spellChecker = (SymSpellChecker) SpellCheckerFactory.createSymSpellChecker(small);
  }

  @Test
  public void check() {
    SpellCheckerTestUtil.printTests(spellChecker);
  }

  @Test
  public void checkSymDict() {
    Map<String, SortedSet<SymSpellNode>> symDict = spellChecker.getSymDict();
    assertTrue(symDict.size() > 0);
    for (Map.Entry<String, SortedSet<SymSpellNode>> entry : symDict.entrySet()) {
      System.out.println("Dictionary entry: " + entry.getKey());
      SortedSet<SymSpellNode> nodes = entry.getValue();
      for (SymSpellNode node : nodes) {
        System.out.println("\t" + node);
      }
    }
  }
}
