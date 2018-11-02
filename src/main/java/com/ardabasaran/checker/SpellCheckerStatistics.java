package com.ardabasaran.checker;

public class SpellCheckerStatistics {
  String checker;
  long initTime;
  long totalCheckTime;
  long numChecks;
  long numCorrect;
  long numFalse;
  long notFound;

  public SpellCheckerStatistics(String checker, long initTime) {
    this.checker = checker;
    this.initTime = initTime;
  }

  public void printStats() {
    System.out.println("Checker: " + checker);
    System.out.println("\tInitialization time: " + initTime);
    System.out.println("\tNumber of checks: " + numChecks);
    System.out.println("\tTotal check time: " + totalCheckTime);
    System.out.println("\tAverage check time: " + ((double) totalCheckTime) / (1000*numChecks) + " s");
    System.out.println("\tNumber of not found words: " + notFound);
    System.out.println("\tNumber of correct checks: " + numCorrect);
    System.out.println("\tNumber of false checks: " + numFalse);
    System.out.println("\tCorrect check ratio: " + ((double) numCorrect) / numChecks * 100 + "%");
  }

  public void incCheckTime(long t) {
    totalCheckTime += t;
  }

  public void incChecks() {
    numChecks += 1;
  }

  public void incChecks(int i) {
    numChecks += i;
  }

  public void incCorrect() {
    numCorrect += 1;
  }

  public void incCorrect(int i) {
    numCorrect += i;
  }

  public void incFalse() {
    numFalse += 1;
  }

  public void incFalse(int i) {
    numFalse += i;
  }

  public void incNotFound() {
    notFound += 1;
  }

  public void incNotFound(int i) {
    notFound += i;
  }

  public String getChecker() {
    return checker;
  }
}
