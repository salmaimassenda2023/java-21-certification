package com.certification.arrays.copying;

import java.util.Arrays;

public class A3_CopyOfRange {
    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4};
        System.out.println(Arrays.toString(Arrays.copyOf(arr, 6)));
        System.out.println(Arrays.toString(Arrays.copyOf(new String[]{"a"}, 3)));
        System.out.println(Arrays.toString(Arrays.copyOfRange(arr, 1, 10)));
        //IllegalArgumentException
        System.out.println(Arrays.toString(Arrays.copyOfRange(arr, 3, 1)));
        //ArrayIndexOutOfBoundsException
        System.out.println(Arrays.toString(Arrays.copyOfRange(arr, 5, 6)));
    }
}
