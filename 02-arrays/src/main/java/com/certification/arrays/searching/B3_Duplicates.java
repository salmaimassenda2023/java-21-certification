package com.certification.arrays.searching;

import java.util.Arrays;

// s'il y a plusieurs éléments égaux, aucune garantie sur lequel est trouve.
public class B3_Duplicates {
    public static void main(String[] args) {
        int[]arr={1, 2, 2, 2, 3};

        System.out.println(Arrays.binarySearch(arr,2));
    }
}
