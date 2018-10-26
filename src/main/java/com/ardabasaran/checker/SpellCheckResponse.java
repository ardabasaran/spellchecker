package com.ardabasaran.checker;

import org.immutables.value.Value;

import java.util.SortedMap;

@Value.Immutable
public interface SpellCheckResponse {
    SortedMap<Integer, String> getCorrectionsByScore();
}
