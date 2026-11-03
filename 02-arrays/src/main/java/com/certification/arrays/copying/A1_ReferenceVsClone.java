package com.certification.arrays.copying;

public class A1_ReferenceVsClone {

    public static void main(String[] args) {
        int[] a = {1, 2, 3};
        int[] b = a;
        int[] c = a.clone();
        a[0] = 99;
        System.out.println(b[0] + " " + c[0]);
    }

}
