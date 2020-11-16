package com.loki.lab.leetcode;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;


/**
 * @author Loki
 * @date 2020/6/2 10:50
 */
class Q64SolutionTest {


    @Test
    void sumNums_give5then15() {
        Q64Solution solution = new Q64Solution();
        int actual = solution.sumNums(5);
        assertEquals(15, actual);
       // assertEquals.assertThat(actual).isEqualTo(15);
    }
}