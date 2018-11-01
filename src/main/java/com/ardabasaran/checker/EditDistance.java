package com.ardabasaran.checker;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.util.List;
import java.util.Set;

public class EditDistance {
  static int editDistance(String wordOne, String wordTwo) {
    int wordOneSize = wordOne.length();
    int wordTwoSize = wordTwo.length();
    int dp[][] = new int[wordOneSize + 1][wordTwoSize + 1];

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

        if (wordOne.charAt(i - 1) == wordTwo.charAt(j - 1)) {
          dp[i][j] = dp[i - 1][j - 1];
          continue;
        }

        dp[i][j] = 1 + Math.min(dp[i - 1][j - 1], Math.min(dp[i - 1][j], dp[i][j - 1]));
      }
    }

    return dp[wordOneSize][wordTwoSize];
  }

  static Set<String> singleEdits(String word) {
    Set<String> edits = Sets.newHashSet();
    List<Split> splits = Lists.newLinkedList();
    String letters = "abcdefghijklmnopqrstuvwxyz";
    for (int i = 0; i < word.length()+1; i++) {
      splits.add(new Split(word.substring(0, i),word.substring(i)));
    }


    for (Split split : splits) {
      String left = split.getLeft();
      String right = split.getRight();

      // deletes
      if (right.length() != 0) {
        edits.add(left + right.substring(1));
      }

      // transposes
      if (right.length() > 1) {
        edits.add(left + right.substring(1,2) + right.substring(0,1) + right.substring(2));
      }

      // replaces & inserts
      for (int i = 0; i < letters.length(); i++) {
        char c = letters.charAt(i);
        // replaces
        if (right.length() > 0) {
          edits.add(left + Character.toString(c) + right.substring(1));
        }
        // inserts
        edits.add(left + Character.toString(c) + right);
      }
    }
    return edits;
  }

  private static class Split {
    String left;
    String right;

    Split(String left, String right) {
      this.left = left;
      this.right = right;
    }

    String getLeft() {
      return left;
    }

    String getRight() {
      return right;
    }
  }
}
