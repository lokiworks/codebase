package com.loki.lab.leetcode;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class Q4SolutionTest {

    @Test
    void findMedianSortedArrays() {
        double actual = Q4Solution.findMedianSortedArrays(new int[]{1,2}, new int[]{-1,3});
        assertEquals(1.5,actual, 0.001);
    }
}