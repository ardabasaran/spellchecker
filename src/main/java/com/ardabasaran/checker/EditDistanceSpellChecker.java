package com.ardabasaran.checker;

import com.google.common.collect.Maps;

import java.util.Map;
import java.util.SortedMap;

/**
 * Naive spellchecking algorithm that computes the distance between two words with product of
 * edit distance and inverse frequency value. Really slow since the word is checked against
 * EVERY word in the dictionary.
 */
public class EditDistanceSpellChecker extends DefaultSpellChecker implements SpellChecker {
  EditDistanceSpellChecker(Map<String, Double> wordsByFrequency,
                           Map<String, Double> wordsByProbability) {
    super(wordsByFrequency, wordsByProbability);
  }

  @Override
  public SpellCheckResponse check(String word) {
    word = word.toLowerCase();
    SortedMap<Double, String> correctionsByScore = Maps.newTreeMap();
    for (Map.Entry<String, Double> entry : wordsByInverseFrequency.entrySet()) {
      String knownWord = entry.getKey();
      double frequency = entry.getValue();
      int distance = EditDistance.editDistance(word, knownWord);

      if (correctionsByScore.size() < NUM_CORRECTIONS) {
        correctionsByScore.put(frequency * distance, knownWord);
      }

      else if (frequency * distance < correctionsByScore.lastKey()) {
        correctionsByScore.put(frequency * distance, knownWord);
        correctionsByScore.remove(correctionsByScore.lastKey());
      }
    }

    return ImmutableSpellCheckResponse.builder()
        .correctionsByScore(correctionsByScore)
        .build();
  }
}
