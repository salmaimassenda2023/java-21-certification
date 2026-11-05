package com.certification.arrays.comparing;

import java.util.Arrays;

public class C3_Mismatch {
    public static void main(String[] args) {

        System.out.println("Test 1: " +
                Arrays.mismatch(new int[]{1, 2, 3}, new int[]{1, 2, 4}));
        // Expected: 2

        System.out.println("Test 2: " +
                Arrays.mismatch(new int[]{1, 2}, new int[]{1, 2}));
        // Expected: -1 (arrays are equal)

        System.out.println("Test 3: " +
                Arrays.mismatch(new int[]{1, 2}, new int[]{1, 2, 3}));
        // Expected: 2 (length of the shorter array)

        System.out.println("Test 4: " +
                Arrays.mismatch(new int[]{1}, null));
        // Expected: NullPointerException
    }
}
