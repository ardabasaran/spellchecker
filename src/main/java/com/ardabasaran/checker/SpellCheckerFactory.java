package com.ardabasaran.checker;

import com.google.common.collect.Maps;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Map;

public class SpellCheckerFactory {
  private static Map<String, Integer> wordsByFrequency;
  private static URL file;

  private static Map<String, Integer> getFrequencyMap(URL file) {
    Map<String, Integer> frequencyMap = Maps.newHashMap();
    try {
      BufferedReader reader = new BufferedReader(new InputStreamReader(file.openStream()));
      String line;
      while ((line = reader.readLine()) != null) {
        String[] words = line.replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+");
        for (String word : words) {
          Integer value = frequencyMap.getOrDefault(word, 0);
          frequencyMap.put(word, value + 1);
        }
      }
    } catch (IOException e) {
      System.err.println("Exception occured while trying to read file: " + file);
    }

    return frequencyMap;
  }

  public static SpellChecker createDefaultSpellChecker(URL file) {
    if (!file.equals(SpellCheckerFactory.file)) {
      wordsByFrequency = getFrequencyMap(file);
      SpellCheckerFactory.file = file;
    }
    return new DefaultSpellChecker(wordsByFrequency);
  }

  public static SpellChecker createEditDistanceSpellChecker(URL file) {
    if (!file.equals(SpellCheckerFactory.file)) {
      wordsByFrequency = getFrequencyMap(file);
      SpellCheckerFactory.file = file;
    }
    return new EditDistanceSpellChecker(wordsByFrequency);
  }
}
