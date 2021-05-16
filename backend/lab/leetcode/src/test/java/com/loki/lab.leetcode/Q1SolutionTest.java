package com.loki.lab.leetcode;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class Q1SolutionTest {

    @Test
    public void twoSum_ShouldFoundIndex(){
        Q1Solution s = new Q1Solution();
        {
            int[] actual = s.twoSum(new int[]{2,7,11,15}, 9);
            int[] expected = new int[]{0,1};
            assertArrayEquals(expected, actual);
        }

        {
            int[] actual = s.twoSum(new int[]{3,2,4}, 6);
            int[] expected = new int[]{1,2};
            assertArrayEquals(expected, actual);
        }
        {
            int[] actual = s.twoSum(new int[]{3,3}, 6);
            int[] expected = new int[]{0,1};
            assertArrayEquals(expected, actual);
        }

    }

    @Test
    public void twoSum_ShouldNotFoundIndex(){
        Q1Solution s = new Q1Solution();
        {
            int[] actual = s.twoSum(new int[]{2,8,11,15}, 9);
            int[] expected = new int[]{};
            assertArrayEquals(expected, actual);
        }

        {
            int[] actual = s.twoSum(new int[]{3,4,4}, 6);
            int[] expected = new int[]{};
            assertArrayEquals(expected, actual);
        }


    }

}
