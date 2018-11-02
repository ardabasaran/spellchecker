package com.ardabasaran.checker;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;

public class SpellCheckerFactory {
  private static Map<String, Double> wordsByInverseFrequency;
  private static Map<String, Double> wordsByProbability;
  private static URL file;

  private static void createMaps(URL file) {
    wordsByInverseFrequency = Maps.newHashMap();
    wordsByProbability = Maps.newHashMap();
    Map<String, Integer> frequencyMap = Maps.newHashMap();
    int numWords = 0;
    try {
      BufferedReader reader = new BufferedReader(new InputStreamReader(file.openStream()));
      String line;
      while ((line = reader.readLine()) != null) {
        String[] words = line.replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+");
        for (String word : words) {
          Integer value = frequencyMap.getOrDefault(word, 0);
          frequencyMap.put(word, value + 1);
          numWords += 1;
        }
      }
    } catch (IOException e) {
      System.err.println("Exception occured while trying to read file: " + file);
    }

    for (Map.Entry<String,Integer> entry : frequencyMap.entrySet()) {
      double inverseFreq = -Math.log(((double) entry.getValue()) / numWords);
      double prob = ((double) entry.getValue()) / numWords;
      wordsByInverseFrequency.put(entry.getKey(), inverseFreq);
      wordsByProbability.put(entry.getKey(), prob);
    }

  }

  public static SpellChecker createDefaultSpellChecker(URL file) {
    if (!file.equals(SpellCheckerFactory.file)) {
      createMaps(file);
      SpellCheckerFactory.file = file;
    }
    return new DefaultSpellChecker(wordsByInverseFrequency, wordsByProbability);
  }

  public static SpellChecker createEditDistanceSpellChecker(URL file) {
    if (!file.equals(SpellCheckerFactory.file)) {
      createMaps(file);
      SpellCheckerFactory.file = file;
    }
    return new EditDistanceSpellChecker(wordsByInverseFrequency, wordsByProbability);
  }

  public static SpellChecker createNorvigSpellChecker(URL file) {
    if (!file.equals(SpellCheckerFactory.file)) {
      createMaps(file);
      SpellCheckerFactory.file = file;
    }
    return new NorvigSpellChecker(wordsByInverseFrequency, wordsByProbability);
  }

  public static SpellChecker createSymSpellChecker(URL file) {
    if (!file.equals(SpellCheckerFactory.file)) {
      createMaps(file);
      SpellCheckerFactory.file = file;
    }
    Set<String> words = wordsByInverseFrequency.keySet();
    Map<String, SortedSet<SymSpellNode>> symDict = buildSymDict(words);
    return new SymSpellChecker(wordsByInverseFrequency, wordsByProbability, symDict);
  }

  private static Map<String, SortedSet<SymSpellNode>> buildSymDict(Set<String> words) {
    Comparator<SymSpellNode> symSpellNodeComparator = (SymSpellNode o1, SymSpellNode o2) -> {
      if (o1.getProbability() < o2.getProbability()) {
        return 1;
      }
      else if (o1.getProbability() > o2.getProbability()) {
        return -1;
      }
      else {
        return o1.getWord().compareTo(o2.getWord());
      }
    };
    Map<String, SortedSet<SymSpellNode>> symDict = Maps.newHashMap();
    for (String word : words) {
      // add original words
      symDict.computeIfAbsent(word, e -> new TreeSet<>(symSpellNodeComparator))
          .add(ImmutableSymSpellNode.builder().probability(1.0).word(word).build());
      // add 1 removals
      List<String> removals = EditDistance.getRemovals(word);
      for (String removal : removals) {
        symDict.computeIfAbsent(removal, e -> new TreeSet<>(symSpellNodeComparator))
            .add(ImmutableSymSpellNode.builder()
                .probability(wordsByProbability.get(word))
                .word(word)
                .build());
      }
    }
    return symDict;
  }
}
