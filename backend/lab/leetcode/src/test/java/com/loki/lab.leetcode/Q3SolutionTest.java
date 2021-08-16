package com.loki.lab.leetcode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
class Q3SolutionTest {

    @Test
    void lengthOfLongestSubstring_expect4_whenGivePwwkew() {
        {
            Q3Solution solution = new Q3Solution();
            Integer len = solution.lengthOfLongestSubstring("abcabcbb");
            Assertions.assertEquals(len, 3);
        }
        Q3Solution solution = new Q3Solution();
        Integer len = solution.lengthOfLongestSubstring("pwwkew");
        Assertions.assertEquals(len, 3);


    }
}