package com.loki.lab.leetcode;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;



class Q43SolutionTest {

    @Test
    void multiply_shouldReturn56088_whenGive123And456() {
        Q43Solution solution  = new Q43Solution();
        String result = solution.multiply("123", "456");
        assertEquals("56088", result);
    }
}