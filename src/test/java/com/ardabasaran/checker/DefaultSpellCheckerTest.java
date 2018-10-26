package com.ardabasaran.checker;

import org.junit.Before;
import org.junit.Test;

import java.net.URL;
import java.util.Map;

import static org.junit.Assert.*;

public class DefaultSpellCheckerTest {
    SpellChecker spellChecker;
    @Before
    public void setUp() throws Exception {
        System.out.println(getClass());
        URL small = DefaultSpellCheckerTest.class.getClassLoader().getResource("small.txt");
        assert small != null;
        spellChecker = SpellCheckerFactory.createDefaultSpellChecker(small);
    }

    @Test
    public void check() {
        assertEquals(0, spellChecker.check("one").getCorrectionsByScore().size());
        assertEquals(0, spellChecker.check("two").getCorrectionsByScore().size());
        assertEquals(0, spellChecker.check("NINE").getCorrectionsByScore().size());
    }

    @Test
    public void frequencyMap() {
        for (Map.Entry<String, Integer> entry : spellChecker.getWordsByFrequency().entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}