package com.loki.lab.leetcode;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Q9SolutionTest {

    @Test
    void isPalindrome() {
     boolean a =    Q9Solution.isPalindrome(121);
     Assert.assertEquals(true,a);
    }
}