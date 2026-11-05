package com.certification.arrays.comparing;

import java.util.Arrays;

public class C1_EqualityTable {
    public static void main(String[] args) {
        int[] x = {1, 2, 3};
        int[] y = {1, 2, 3};
        int[][] m1 = {{1, 2}, {3}};
        int[][] m2 = {{1, 2}, {3}};

        System.out.println(x == y); // false
        System.out.println(x.equals(y)); // false
        System.out.println(Arrays.equals(x,y)); // true
        System.out.println(Arrays.equals(m1,m2)); // false
        System.out.println(Arrays.deepEquals(m1,m2)); // true

    }
}
