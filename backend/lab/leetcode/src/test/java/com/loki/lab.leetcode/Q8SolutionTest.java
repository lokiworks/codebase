package com.loki.lab.leetcode;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;



class Q8SolutionTest {

    @Test
    void myAtoi() {
        int r = Q8Solution.myAtoi2("2147483648");
        assertEquals(2147483647,r);
    }
}