package com.ardabasaran.checker;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import java.util.*;

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
    TreeMap<Double, String> correctionsByScore = Maps.newTreeMap(Collections.reverseOrder());
    // check original
    SortedSet<SymSpellNode> nodes = symDict.getOrDefault(word, new TreeSet<>());
    for (SymSpellNode node : nodes) {
      addNode(correctionsByScore, 1.0, node);
    }
    // check one edits
    List<String> edits = Lists.newLinkedList();
    edits.addAll(EditDistance.getRemovals(word));
    for (String edit : edits) {
      SortedSet<SymSpellNode> editedNodes = symDict.getOrDefault(edit, new TreeSet<>());
      for (SymSpellNode node : editedNodes) {
        addNode(correctionsByScore, 2.0, node);
      }
    }

    if (correctionsByScore.size() == 0) {
      correctionsByScore.put(1.0, word);
    }

    // return results
    return ImmutableSpellCheckResponse.builder()
        .correctionsByScore(correctionsByScore)
        .build();
  }

  public Map<String, SortedSet<SymSpellNode>> getSymDict() {
    return symDict;
  }

  private void addNode(TreeMap<Double, String> correctionsByScore, double distance, SymSpellNode node) {
    if (correctionsByScore.size() < NUM_CORRECTIONS) {
      correctionsByScore.put(node.getProbability()/distance, node.getWord());
    }
    else if (node.getProbability() < correctionsByScore.lastKey()) {
      correctionsByScore.put(node.getProbability()/distance, node.getWord());
      correctionsByScore.remove(correctionsByScore.lastKey());
    }
  }
}
