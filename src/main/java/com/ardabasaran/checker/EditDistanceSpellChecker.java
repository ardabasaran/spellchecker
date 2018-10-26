package com.ardabasaran.checker;

import com.google.common.collect.Maps;

import java.util.Map;
import java.util.SortedMap;

public class EditDistanceSpellChecker extends DefaultSpellChecker implements SpellChecker {
  EditDistanceSpellChecker(Map<String, Integer> wordsByFrequency) {
    super(wordsByFrequency);
  }

  @Override
  public SpellCheckResponse check(String word) {
    word = word.toLowerCase();
    SortedMap<Integer, String> correctionsByScore = Maps.newTreeMap();
    for (Map.Entry<String, Integer> entry : wordsByFrequency.entrySet()) {
      String knownWord = entry.getKey();
      int frequency = entry.getValue();
      int distance = EditDistance.editDistance(word, knownWord);
      if (correctionsByScore.size() > 10 && frequency * distance < correctionsByScore.lastKey()) {
        correctionsByScore.put(frequency * distance, knownWord);
        correctionsByScore.remove(correctionsByScore.lastKey());
      }
    }

    return ImmutableSpellCheckResponse.builder()
        .correctionsByScore(correctionsByScore)
        .build();
  }
}
