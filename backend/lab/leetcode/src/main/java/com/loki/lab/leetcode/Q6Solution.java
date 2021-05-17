package com.loki.lab.leetcode;

public class Q6Solution {
    public static  String convert(String s, int numRows) {
        if (numRows == 1){
            return  s;
        }

        StringBuilder ret = new StringBuilder();
        int n = s.length();
        int cycleLen = 2*(numRows-1);

        for (int i = 0; i < numRows; i++){
            for (int j = 0; i+j < n; j+= cycleLen){
                ret.append(s.charAt(i+j));
                if (i != 0 && i != numRows-1 && cycleLen+j-i < n){
                    ret.append(s.charAt(cycleLen+j-i));
                }
            }
        }
        return  ret.toString();
    }

}
