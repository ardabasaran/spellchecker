package com.ardabasaran.checker;

import com.ardabasaran.checker.ImmutableSpellCheckResponse;
import com.google.common.collect.Maps;

import java.util.Map;

public class DefaultSpellChecker implements SpellChecker {
    Map<String, Integer> wordsByFrequency;

    DefaultSpellChecker() {
        wordsByFrequency = Maps.newHashMap();
    }

    DefaultSpellChecker(Map<String, Integer> wordsByFrequency) {
        this.wordsByFrequency = wordsByFrequency;
    }

    @Override
    public SpellCheckResponse check(String word) {
        return ImmutableSpellCheckResponse.builder().build();
    }

    @Override
    public Map<String, Integer> getWordsByFrequency() {
        return wordsByFrequency;
    }
}
