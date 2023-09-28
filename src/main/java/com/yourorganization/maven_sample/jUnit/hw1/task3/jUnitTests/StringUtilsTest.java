package com.yourorganization.maven_sample.jUnit.hw1.task3.jUnitTests;

import com.yourorganization.maven_sample.jUnit.hw1.task3.models.StringUtils;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StringUtilsTest {

    @Test
    public void testIsPalindrome() {
        assertTrue(StringUtils.isPalindrome("racecar"));
        assertTrue(StringUtils.isPalindrome("A man a plan a canal Panama"));
        assertFalse(StringUtils.isPalindrome("hello"));
    }

    @Test
    public void testCountVowels() {
        assertEquals(2, StringUtils.countVowels("hello"));
        assertEquals(3, StringUtils.countVowels("programming"));
    }

    @Test
    public void testCountConsonants() {
        assertEquals(3, StringUtils.countConsonants("hello"));
        assertEquals(8, StringUtils.countConsonants("programming"));
    }

    @Test
    public void testCountWordOccurrences() {
        assertEquals(2, StringUtils.countWordOccurrences("Hello, world! Hello, universe!", "Hello"));
        assertEquals(2, StringUtils.countWordOccurrences("Java is a programming language. Java is widely used.", "Java"));
    }
}

