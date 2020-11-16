package com.loki.lab.leetcode;

import java.util.*;

/**
 * 给你一个二叉树，请你返回其按 层序遍历 得到的节点值。 （即逐层地，从左到右访问所有节点）。
 * <p>
 *  
 * <p>
 * 示例：
 * 二叉树：[3,9,20,null,null,15,7],
 * <p>
 * 3
 * / \
 * 9  20
 * /  \
 * 15   7
 * 返回其层次遍历结果：
 * <p>
 * [
 * [3],
 * [9,20],
 * [15,7]
 * ]
 * 通过次数133,635提交次数212,583
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/binary-tree-level-order-traversal
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * @author Loki
 */
public class Q102Solution {
    public List<List<Integer>> levelOrderWithDFS(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        levelOrderWithDFS(result, 0, root);
        return result;
    }

    public void levelOrderWithDFS(List<List<Integer>> result, int level, TreeNode node) {
        if (node == null) {
            return;
        }
        if (result.size() == level) {
            result.add(new ArrayList<Integer>());
        }
        List<Integer> sub = result.get(level);
        sub.add(node.val);

        levelOrderWithDFS(result, level + 1, node.left);
        levelOrderWithDFS(result, level + 1, node.right);

    }

    public List<List<Integer>> levelOrderWithBFS(TreeNode root) {
        if (root == null) {
            return Collections.emptyList();
        }
        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.add(root);

        List<List<Integer>> result = new ArrayList<>();

        while (!queue.isEmpty()) {
            int size = queue.size();
            List<Integer> sub = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                sub.add(node.val);
                if (node.left != null) {
                    queue.add(node.left);
                }

                if (node.right != null) {
                    queue.add(node.right);
                }
            }

            result.add(sub);

        }

        return result;
    }


    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }
}
