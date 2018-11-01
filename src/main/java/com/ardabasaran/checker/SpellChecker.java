package com.ardabasaran.checker;

import java.util.Map;

public interface SpellChecker {
  int NUM_CORRECTIONS = 5;

  public SpellCheckResponse check(String word);

  public Map<String, Double> getWordsByInverseFrequency();

  public Map<String, Double> getWordsByProbability();
}
