package com.ardabasaran.checker;

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
}
