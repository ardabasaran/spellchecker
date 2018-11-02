package com.ardabasaran;

import com.ardabasaran.checker.SpellChecker;
import com.ardabasaran.checker.SpellCheckerFactory;
import com.ardabasaran.checker.SpellCheckerStatistics;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

public class SpellCheckComparison {
  public static void main(String[] args) {
    URL textFile = SpellCheckComparison.class.getClassLoader().getResource("big.txt");
    assert textFile != null;

    Map<String, List<String>> testData = getTestData();
    Instant start, finish;
    long timeElapsed;

    start = Instant.now();
    SpellChecker editDistanceChecker = SpellCheckerFactory.createEditDistanceSpellChecker(textFile);
    finish = Instant.now();
    timeElapsed = Duration.between(start, finish).toMillis();
    SpellCheckerStatistics editDistanceStats = new SpellCheckerStatistics("Edit Distance", timeElapsed);

    start = Instant.now();
    SpellChecker norvigChecker = SpellCheckerFactory.createNorvigSpellChecker(textFile);
    finish = Instant.now();
    timeElapsed = Duration.between(start, finish).toMillis();
    SpellCheckerStatistics norvigStats = new SpellCheckerStatistics("Norvig", timeElapsed);

    start = Instant.now();
    SpellChecker symChecker = SpellCheckerFactory.createSymSpellChecker(textFile);
    finish = Instant.now();
    timeElapsed = Duration.between(start, finish).toMillis();
    SpellCheckerStatistics symSpellStats = new SpellCheckerStatistics("SymSpell", timeElapsed);

    testChecker(editDistanceChecker, editDistanceStats, testData, true);
    testChecker(norvigChecker, norvigStats, testData, true);
    testChecker(symChecker, symSpellStats, testData, true);

    editDistanceStats.printStats();
    norvigStats.printStats();
    symSpellStats.printStats();
  }

  private static Map<String, List<String>> getTestData() {
    Map<String, List<String>> data = Maps.newHashMap();

    URL textFile1 = SpellCheckComparison.class.getClassLoader().getResource("testSet1.txt");
    URL textFile2 = SpellCheckComparison.class.getClassLoader().getResource("testSet2.txt");

    readTestFile(textFile1, data);
    readTestFile(textFile2, data);

    return data;
  }

  private static void readTestFile(URL textFile, Map<String, List<String>> data) {
    try {
      BufferedReader reader = new BufferedReader(new InputStreamReader(textFile.openStream()));
      String line;
      while ((line = reader.readLine()) != null) {
        String[] words = line.split("\\s+");
        if (words[0].charAt(words[0].length()-1) == ':') {
          String key = words[0].toLowerCase().substring(0, words[0].length()-1);
          for (int i = 1; i < words.length; i++) {
            data.computeIfAbsent(key, k -> Lists.newLinkedList()).add(words[i].toLowerCase());
          }
        }
        else {
          String key = words[1].toLowerCase();
          String value = words[0].toLowerCase();
          data.computeIfAbsent(key, k -> Lists.newLinkedList()).add(value);
        }
      }
    } catch (NullPointerException | IOException e) {
      System.err.println("Test file is not found / can't be read!");
      System.exit(0);
    }
  }

  private static void testChecker(SpellChecker checker,
                                  SpellCheckerStatistics stats,
                                  Map<String, List<String>> data,
                                  boolean verbose) {
    if (verbose) {
      System.out.println("Testing " + stats.getChecker());
      System.out.println("\tTotal number of tests: " + data.size());
    }
    int testCounter = 0;
    for (Map.Entry<String, List<String>> entry : data.entrySet()) {
      if(verbose && testCounter % 1000 == 0) {
        System.out.println("\tTest number: " + testCounter);
      }
      testCounter += 1;
      Map<String, Double> dict = checker.getWordsByProbability();
      Instant start, finish;
      long timeElapsed;
      String word = entry.getKey();
      List<String> testWords = entry.getValue();
      for (String testWord : testWords) {
        stats.incChecks();
        start = Instant.now();
        SortedMap<Double, String> corrections = checker.check(testWord).getCorrectionsByScore();
        String first_correction = corrections.get(corrections.firstKey());
        if (dict.containsKey(word)) {
          if (word.equals(first_correction)) {
            stats.incCorrect();
          } else {
            stats.incFalse();
          }
        } else {
          stats.incFalse();
          stats.incNotFound();
        }
        finish = Instant.now();
        timeElapsed = Duration.between(start, finish).toMillis();
        stats.incCheckTime(timeElapsed);
      }
    }
  }
}
