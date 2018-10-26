package com.ardabasaran.checker;

import java.util.Map;
import org.immutables.value.Value;

@Value.Immutable
public interface SpellCheckResponse {
  Map<Integer, String> getCorrectionsByScore();
}
