package com.loki.lab.leetcode;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class Q2SolutionTest {
    @Test
    public void addTwoNumbers() {
        Q2Solution solution = new Q2Solution();
        Q2Solution.ListNode n1 = new Q2Solution.ListNode();
        n1.val = 9;
        {
            Q2Solution.ListNode n2 = new Q2Solution.ListNode();
            n2.val = 9;

            Q2Solution.ListNode n3 = new Q2Solution.ListNode();
            n3.val = 9;
            Q2Solution.ListNode n4 = new Q2Solution.ListNode();
            n4.val = 9;
            Q2Solution.ListNode n5 = new Q2Solution.ListNode();
            n5.val = 9;
            Q2Solution.ListNode n6 = new Q2Solution.ListNode();
            n6.val = 9;
            Q2Solution.ListNode n7 = new Q2Solution.ListNode();
            n7.val = 9;

            n1.next = n2;
            n2.next = n3;
            n3.next = n4;
            n4.next = n5;
            n5.next = n6;
            n6.next = n7;
        }

        Q2Solution.ListNode n4 = new Q2Solution.ListNode();
        n4.val = 9;
        {


            Q2Solution.ListNode n5 = new Q2Solution.ListNode();
            n5.val = 9;

            Q2Solution.ListNode n6 = new Q2Solution.ListNode();
            n6.val = 9;
            Q2Solution.ListNode n7 = new Q2Solution.ListNode();
            n7.val = 9;


            n4.next = n5;
            n5.next = n6;
            n6.next = n7;

        }

        Q2Solution.ListNode r = solution.addTwoNumbers(n1, n4);

        List<Integer> actualList = Arrays.asList(8, 9, 9, 9, 0, 0, 0, 1);
        int i = 0;
        while (r != null) {
            Assert.assertEquals(r.val, (int) actualList.get(i));
            r = r.next;
            ++i;
        }
    }
}
