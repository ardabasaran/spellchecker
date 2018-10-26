package com.ardabasaran.checker;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EditDistanceTest {
  @Test
  public void editDistance() {
    assertEquals(2, EditDistance.editDistance("book", "back"));
    assertEquals(4, EditDistance.editDistance("one", "three"));
    assertEquals(7, EditDistance.editDistance("distance", "school"));

    // check edge cases
    assertEquals(0, EditDistance.editDistance("", ""));
    assertEquals(1, EditDistance.editDistance("", "a"));
    assertEquals(1, EditDistance.editDistance("b", ""));
    assertEquals(4, EditDistance.editDistance("", "rest"));
    assertEquals(5, EditDistance.editDistance("music", ""));
  }
}