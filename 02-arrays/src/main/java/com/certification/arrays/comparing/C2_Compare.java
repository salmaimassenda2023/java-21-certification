package com.certification.arrays.comparing;

import java.util.Arrays;

public class C2_Compare {
    public static void main(String[] args) {
        System.out.println(Arrays.compare(new int[]{1,2,3}, new int[]{1,2,4}));
           // -1  (3 < 4 at first mismatch)
        Arrays.compare(new int[]{1,2,3}, new int[]{1,2});     //  1  (positive: longer is greater)
        Arrays.compare(new int[]{1,2,3}, new int[]{1,2,3});   //  0
        Arrays.compare(null, new int[]{1}) ;          // -1  (null is smaller)
        Arrays.compare(new int[]{1}, null) ;                  //  1
        Arrays.compare((int[]) null, (int[]) null)  ;         //  0
    }
}
