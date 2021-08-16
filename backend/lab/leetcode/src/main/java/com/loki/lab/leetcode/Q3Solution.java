package com.loki.lab.leetcode;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * https://leetcode-cn.com/problems/longest-substring-without-repeating-characters/
 */
public class Q3Solution {
    public int lengthOfLongestSubstring(String s) {
        StringBuilder substr = new StringBuilder(8);
        int longest = 0;
        for (int i = 0; i < s.length(); i++){
            char c = s.charAt(i);
            int index  = substr.indexOf(String.valueOf(c));
            if (index != -1){
                if (substr.length() > longest){
                    longest = substr.length() ;
                }
                String tmp = substr.substring(index + 1);
                substr = new StringBuilder(tmp);
            }
                substr.append(c);
        }

        if (substr.length() > longest){
            longest = substr.length();
        }
        return longest;
    }

    public int lengthOfLongestSubstring2(String s) {
        if (s.length() == 0){
            return 0;
        }

        HashMap<Character, Integer> map = new HashMap<Character, Integer>();
        int max = 0;
        int left = 0;
        for (int i = 0; i < s.length(); i++){
            if (map.containsKey(s.charAt(i))){
                left = Math.max(left, map.get(s.charAt(i)) + 1);
            }
            map.put(s.charAt(i), i);
            max = Math.max(max, i-left+1);
        }
        return max;

    }
}
