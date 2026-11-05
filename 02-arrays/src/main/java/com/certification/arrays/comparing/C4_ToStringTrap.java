package com.certification.arrays.comparing;

import java.util.Arrays;

public class C4_ToStringTrap {
    public static void main(String[] args) {
        int[][] m1 = {{1, 2}, {3}};
        int[][] m2 = {{1, 2}, {3}};
        System.out.println(Arrays.toString(m1));
        System.out.println(Arrays.deepToString(m1));
        System.out.println(Arrays.deepHashCode(m1) == Arrays.deepHashCode(m2));
        System.out.println(Arrays.hashCode(new int[]{1, 2}) == Arrays.hashCode(new int[]{1, 2}));
    }
}
