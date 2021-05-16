package com.loki.lab.leetcode;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Q4SolutionTest {

    @Test
    void findMedianSortedArrays() {
        double actual = Q4Solution.findMedianSortedArrays(new int[]{1,2}, new int[]{-1,3});
        Assert.assertEquals(1.5,actual, 0.001);
    }
}