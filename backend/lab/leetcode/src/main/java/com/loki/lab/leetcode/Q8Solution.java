package com.loki.lab.leetcode;

import java.util.HashMap;
import java.util.Map;

public class Q8Solution {

    static class Automation{
        public int sign = 1;
        public long ans = 0;
        private String state = "start";
        
        private Map<String, String[]> table = new HashMap<String, String[]>(){{
            put("start", new String[]{"start", "signed", "in_number", "end"});
            put("signed", new String[]{"end", "end", "in_number", "end"});
            put("in_number", new String[]{"end", "end", "in_number", "end"});
            put("end", new String[]{"end", "end", "end", "end"});
        }};

        public void get(char c){
              state =   table.get(state)[get_col(c)];
              if("in_number".equals(state)){
                  ans = ans*10 + c - '0';
                  ans = sign == 1 ? Math.min(ans, (long) Integer.MAX_VALUE):Math.max(ans, -(long)Integer.MIN_VALUE);
              }else if ("signed".equals(state)){
                  sign = c == '+' ? 1:-1;
              }
        }

        private int get_col(char c){
            if (c == ' '){
                return 0;
            }
            if (c == '+' || c == '-'){
                return 1;
            }
            if (Character.isDigit(c)){
                return 2;
            }
            return 3;
        }
    }


    public static int myAtoi2(String s){
        Automation a = new Automation();
        for (int i = 0; i < s.length(); i++){
            a.get(s.charAt(i));
        }
        return (int)(a.sign * a.ans);
    }

    public static int myAtoi(String s) {
        int ret = 0;
        int flag = 0;
        boolean r = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (r == false && c == ' ') {
                continue;
            }
            r = true;

            if (flag == 0 && (c == '+' || c == '-')) {
                if (c == '-') {
                    flag = -1;
                }else{
                    flag = 1;
                }
                continue;
            }

            if (flag == 0){
                flag = 1;
            }

            int num = c - '0';
            if (!(num >= 0 && num <= 9)) {
                break;
            }

            if (ret*flag > Integer.MAX_VALUE / 10 || (ret*flag == Integer.MAX_VALUE/10 && num >7)) {
                return Integer.MAX_VALUE;
            }

            if (ret*flag < Integer.MIN_VALUE / 10 || (ret*flag == Integer.MIN_VALUE/10 && num > 8)) {
                return Integer.MIN_VALUE;
            }

            ret = 10 * ret + num;

        }
        return ret*flag;
    }
}
