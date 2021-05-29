package com.loki.lab.leetcode;

import org.junit.Assert;
import org.junit.jupiter.api.Test;


class Q8SolutionTest {

    @Test
    void myAtoi() {
        int r = Q8Solution.myAtoi2("2147483648");
        Assert.assertEquals(2147483647,r);
    }
}