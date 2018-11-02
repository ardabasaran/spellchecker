package com.ardabasaran.checker;

import java.util.SortedMap;

import org.immutables.value.Value;

@Value.Immutable
public interface SpellCheckResponse {
  SortedMap<Double, String> getCorrectionsByScore();
}
