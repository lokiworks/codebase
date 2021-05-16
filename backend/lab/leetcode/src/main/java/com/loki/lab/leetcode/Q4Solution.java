package com.loki.lab.leetcode;

public class Q4Solution {
    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int n1Len = nums1.length;
        int n2Len = nums2.length;
        int len = n1Len + n2Len;

        int curVal = 0;
        int prevVal = 0;

        int n1CurIdx = 0;
        int n2CurIdx = 0;

        for (int i = 0; i <= len/2; i++){
            prevVal = curVal;
            if (n1CurIdx < n1Len && (n2CurIdx >= n2Len || nums1[n1CurIdx] < nums2[n2CurIdx])){
                curVal = nums1[n1CurIdx++];
            }else{
                curVal  = nums2[n2CurIdx++];
            }
        }

        if ((len&1)==0){
            return (curVal + prevVal)/2.0;
        }else{
            return curVal;
        }
    }
}
