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

  @Test
  public void singleEdits() {
    // tested against edits1 method which can be found here:
    // http://norvig.com/spell-correct.html
    assertEquals(284, EditDistance.singleEdits("hello").size());
    assertEquals(494, EditDistance.singleEdits("something").size());
    assertEquals(232, EditDistance.singleEdits("book").size());
    assertEquals(390, EditDistance.singleEdits("program").size());
    assertEquals(546, EditDistance.singleEdits("terminator").size());
  }
}