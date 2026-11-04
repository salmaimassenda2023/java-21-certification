package com.certification.arrays.copying;

import java.util.Arrays;

// Remarque:
// ArrayStoreException est une exception Java qui se produit lorsqu'on essaie
// de stocker un objet dans un tableau
// alors que son type réel n'est pas compatible avec le type du tableau.

public class A4_ArrayCopy {

    public static void main(String[] args) {
        int[] a = {1, 2, 3, 4, 5};
        System.arraycopy(a, 0, a, 1, 4);
        System.out.println(Arrays.toString(a));
    }
}
