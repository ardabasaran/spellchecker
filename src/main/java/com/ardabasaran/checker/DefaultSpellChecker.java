package com.ardabasaran.checker;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * Spellchecker that doesn't do actual checking. Corrections collection returned will always
 * be empty.
 */
public class DefaultSpellChecker implements SpellChecker {
  Map<String, Double> wordsByInverseFrequency;
  Map<String, Double> wordsByProbability;

  DefaultSpellChecker() {
    wordsByInverseFrequency = Maps.newHashMap();
  }

  DefaultSpellChecker(Map<String, Double> wordsByInverseFrequency,
                      Map<String, Double> wordsByProbability) {
    this.wordsByInverseFrequency = wordsByInverseFrequency;
    this.wordsByProbability = wordsByProbability;
  }

  @Override
  public SpellCheckResponse check(String word) {
    return ImmutableSpellCheckResponse.builder().build();
  }

  @Override
  public Map<String, Double> getWordsByInverseFrequency() {
    return wordsByInverseFrequency;
  }

  @Override
  public Map<String, Double> getWordsByProbability() {
    return wordsByProbability;
  }
}
