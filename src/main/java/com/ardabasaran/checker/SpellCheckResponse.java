package com.ardabasaran.checker;

import org.immutables.value.Value;

import java.util.Map;
import java.util.SortedMap;

@Value.Immutable
public interface SpellCheckResponse {
    Map<Integer, String> getCorrectionsByScore();
}
