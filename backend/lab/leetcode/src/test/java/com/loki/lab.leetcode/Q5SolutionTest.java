package com.loki.lab.leetcode;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Q5SolutionTest {

    @Test
    void longestPalindrome_expectbab_givebabad() {
       assertEquals(3,Q5Solution.longestPalindrome("babad").length()) ;
    }


    @Test
    void longestPalindrome_expectbb_givecbbd() {
        assertEquals("bb",Q5Solution.longestPalindrome("cbbd")) ;
    }


    @Test
    void longestPalindrome_expectbb_givebb() {
        assertEquals("bb",Q5Solution.longestPalindrome("bb")) ;
    }
}