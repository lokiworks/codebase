package com.loki.lab.leetcode;


import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Q102SolutionTest {


    @Test
    public void levelOrderWithDFS() {
        // Given
        Q102Solution.TreeNode rootNode = new Q102Solution.TreeNode(1);
        Q102Solution.TreeNode left1Node = new Q102Solution.TreeNode(2);
        Q102Solution.TreeNode right2Node = new Q102Solution.TreeNode(3);
        Q102Solution.TreeNode left21Node = new Q102Solution.TreeNode(4);
        Q102Solution.TreeNode right22Node = new Q102Solution.TreeNode(5);
        rootNode.left = left1Node;
        rootNode.right = right2Node;
        right2Node.left = left21Node;
        right2Node.right = right22Node;

        // When
        Q102Solution q102Solution = new Q102Solution();
        List<List<Integer>> actual = q102Solution.levelOrderWithDFS(rootNode);

        // Then
        List<List<Integer>> expect = new ArrayList<>();
        expect.add(Arrays.asList(1));
        expect.add(Arrays.asList(2, 3));
        expect.add(Arrays.asList(4, 5));
        assertEquals(expect, actual);


    }

    @Test
    public void levelOrderWithBFS() {
        // Given
        Q102Solution.TreeNode rootNode = new Q102Solution.TreeNode(1);
        Q102Solution.TreeNode left1Node = new Q102Solution.TreeNode(2);
        Q102Solution.TreeNode right2Node = new Q102Solution.TreeNode(3);
        Q102Solution.TreeNode left21Node = new Q102Solution.TreeNode(4);
        Q102Solution.TreeNode right22Node = new Q102Solution.TreeNode(5);
        rootNode.left = left1Node;
        rootNode.right = right2Node;
        right2Node.left = left21Node;
        right2Node.right = right22Node;

        // When
        Q102Solution q102Solution = new Q102Solution();
        List<List<Integer>> actual = q102Solution.levelOrderWithDFS(rootNode);

        // Then
        List<List<Integer>> expect = new ArrayList<>();
        expect.add(Arrays.asList(1));
        expect.add(Arrays.asList(2, 3));
        expect.add(Arrays.asList(4, 5));
        assertEquals(expect, actual);

    }
}