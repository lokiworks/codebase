package com.loki.lab.leetcode;

/**
 * https://leetcode-cn.com/problems/multiply-strings/
 */

import java.util.Arrays;

/**
 * 13
 * 14
 * <p>
 * 12
 * 4
 * 3
 * 1
 * ----
 * 182
 */
public class Q43Solution {
    public String multiply(String num1, String num2) {
        char[] a = new char[num1.length() + num2.length()];
        Arrays.fill(a, '0');
        for (int i = num1.length() - 1; i >= 0; i--) {
            int n1 = num1.charAt(i) - '0';
            for (int j = num2.length() - 1; j >= 0; j--) {
                int n2 = num2.charAt(j) - '0';
                int temp = a[i + j + 1] - '0' + n1 * n2;
                a[i + j + 1] = (char) (temp % 10 + '0');
                a[i + j] += (char) (temp / 10);
            }
        }

        for (int i = 0; i < num1.length() + num2.length(); ++i) {
            if (a[i] != '0') {
                return new String(a).substring(i);
            }
        }
        return "0";
    }
}
