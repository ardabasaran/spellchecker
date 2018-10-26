package com.ardabasaran.checker;

import com.ardabasaran.checker.ImmutableSpellCheckResponse;
import com.google.common.collect.Maps;

import java.util.Map;
import java.util.SortedMap;

public class EditDistanceSpellChecker extends DefaultSpellChecker implements SpellChecker {
    EditDistanceSpellChecker(Map<String, Integer> wordsByFrequency) {
        super (wordsByFrequency);
    }

    @Override
    public SpellCheckResponse check(String word) {
        word = word.toLowerCase();
        SortedMap<Integer, String> correctionsByScore = Maps.newTreeMap();
        for (Map.Entry<String, Integer> entry : wordsByFrequency.entrySet()) {
            String knownWord = entry.getKey();
            int frequency = entry.getValue();
            int distance = editDistance(word, knownWord);
            if (correctionsByScore.size() > 10 && frequency * distance < correctionsByScore.lastKey()) {
                correctionsByScore.put(frequency * distance, knownWord);
                correctionsByScore.remove(correctionsByScore.lastKey());
            }
        }

        return ImmutableSpellCheckResponse.builder()
                .correctionsByScore(correctionsByScore)
                .build();
    }

    private int editDistance(String wordOne, String wordTwo) {
        int wordOneSize = wordOne.length();
        int wordTwoSize = wordTwo.length();
        int dp[][] = new int[wordOneSize+1][wordTwoSize+1];

        for (int i = 0; i <= wordOneSize; i++) {
            for (int j = 0; j <= wordTwoSize; j++) {
                if (i == 0) {
                    dp[i][j] = j;
                    continue;
                }

                if (j == 0) {
                    dp[i][j] = i;
                    continue;
                }

                if (wordOne.charAt(i-1) == wordTwo.charAt(j-1)) {
                    dp[i][j] = dp[i-1][j-1];
                    continue;
                }

                dp[i][j] = 1 + Math.min(dp[i-1][j-1], Math.min(dp[i-1][j], dp[i][j-1]));
            }
        }

        return dp[wordOneSize][wordTwoSize];
    }
}
