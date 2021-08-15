package com.loki.lab.leetcode;

public class Q9Solution {
    public static boolean isPalindrome(int x){
        if(x < 0){
            return false;
        }
        int res = 0;
        int tmp = x;
        while(tmp>0){
            if(res > Integer.MAX_VALUE/10){
                return false;
            }
            if(res < Integer.MIN_VALUE/10){
                return false;
            }
            res = 10 * res + tmp%10;
            tmp = tmp/10;
        }
        return res == x;
    }
}
