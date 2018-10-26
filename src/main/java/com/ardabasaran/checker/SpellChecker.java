package com.ardabasaran.checker;

import java.util.Map;

public interface SpellChecker {
  public SpellCheckResponse check(String word);

  public Map<String, Integer> getWordsByFrequency();
}
