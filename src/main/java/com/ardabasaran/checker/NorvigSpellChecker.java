package com.ardabasaran.checker;

import com.google.common.collect.*;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * Implementation of spellchecking algorithm proposed by Peter Norvig.
 * See: http://norvig.com/spell-correct.html
 */
public class NorvigSpellChecker extends DefaultSpellChecker implements SpellChecker {
  NorvigSpellChecker(Map<String, Double> wordsByFrequency,
                           Map<String, Double> wordsByProbability) {
    super(wordsByFrequency, wordsByProbability);
  }

  @Override
  public SpellCheckResponse check(String word) {
    TreeMap<Double, String> corrections = Maps.newTreeMap(Collections.reverseOrder());

    // check the original
    if (wordsByInverseFrequency.containsKey(word)) {
      corrections.put(0.0, word);
    }
    // 1 edit distance away
    else {
      Set<String> allSingleAway = EditDistance.singleEdits(word);
      Set<String> candidates = getKnownWords(allSingleAway);
      candidates.forEach(c -> addCandidate(c, corrections));
      // 2 edit distance away
      if (corrections.size() < NUM_CORRECTIONS) {
        Set<String> allTwoAway = Sets.newHashSet();
        for (String single : allSingleAway) {
          allTwoAway.addAll(EditDistance.singleEdits(single));
        }
        Set<String> candidatesTwoAway = getKnownWords(allTwoAway);
        candidatesTwoAway.stream()
            .filter(e -> !candidates.contains(e))
            .forEach(c -> addCandidate(c, corrections));
      }
    }

    if (corrections.size() == 0) {
      corrections.put(0.0, word);
    }

    // return results
    return ImmutableSpellCheckResponse.builder()
        .correctionsByScore(corrections)
        .build();
  }

  private Set<String> getKnownWords(Set<String> allWords) {
    return ImmutableSet.<String>builder()
        .addAll(allWords.stream()
            .filter(e -> wordsByInverseFrequency.containsKey(e))
            .collect(Collectors.toSet()))
        .build();
  }

  private void addCandidate(String candidate,
                            TreeMap<Double, String> correctionsByScore) {
    double cost = wordsByProbability.get(candidate);
    if (correctionsByScore.size() < NUM_CORRECTIONS) {
      correctionsByScore.put(cost, candidate);
    }

    else if (cost < correctionsByScore.lastKey()) {
      correctionsByScore.put(cost, candidate);
      correctionsByScore.remove(correctionsByScore.lastKey());
    }
  }
}
