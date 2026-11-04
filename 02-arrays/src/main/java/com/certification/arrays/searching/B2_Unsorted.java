package com.certification.arrays.searching;

import java.util.Arrays;

// si le tableau n est pas trié : Aucune exception
// Mais resultat n est pas fiable (indéfini)
public class B2_Unsorted {
    public static void main(String[] args) {
        int[]arr={5, 1, 4, 2, 3};
        System.out.println(Arrays.binarySearch(arr,2));
    }
}
