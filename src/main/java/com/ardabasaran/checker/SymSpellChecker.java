package com.ardabasaran.checker;

import java.util.Map;
import java.util.SortedSet;

public class SymSpellChecker extends DefaultSpellChecker implements SpellChecker {
  Map<String, SortedSet<SymSpellNode>> symDict;

  SymSpellChecker(Map<String, Double> wordsByFrequency,
                  Map<String, Double> wordsByProbability,
                  Map<String, SortedSet<SymSpellNode>> symDict) {
    super(wordsByFrequency, wordsByProbability);
    this.symDict = symDict;
  }

  @Override
  public SpellCheckResponse check(String word) {
    return null;
  }

  public Map<String, SortedSet<SymSpellNode>> getSymDict() {
    return symDict;
  }
}
