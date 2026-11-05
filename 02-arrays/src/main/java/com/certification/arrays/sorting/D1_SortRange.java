package com.certification.arrays.sorting;

import java.util.Arrays;

//  from > to -> IllegalArgumentException ;
//  from < 0 ou to > length -> ArrayIndexOutOfBoundsException.
public class D1_SortRange {
    public static void main(String[] args) {
        int[] a = {9, 7, 5, 3, 1};
        Arrays.sort(a, 1, 4);
        System.out.println(Arrays.toString(a));   // [9, 3, 5, 7, 1]
    }
}
