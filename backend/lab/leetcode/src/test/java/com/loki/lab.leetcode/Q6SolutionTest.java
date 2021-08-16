package com.loki.lab.leetcode;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Q6SolutionTest {

    @Test
    void convert_expectA() {
      String expect =   Q6Solution.convert("A", 2);
        assertEquals(expect, "A");
    }
}