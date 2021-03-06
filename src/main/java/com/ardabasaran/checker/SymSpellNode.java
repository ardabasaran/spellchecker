package com.ardabasaran.checker;

import org.immutables.value.Value;

@Value.Immutable
public interface SymSpellNode {
  double getProbability();

  String getWord();
}
