package com.certification.arrays.sorting;

import java.util.Arrays;

public class D2_StringOrder {
    public static void main(String[] args) {
        String[] fruits = {
                "banana", "Apple", "cherry", "apple", "10", "2", "Banana"
        };

        //Arrays.sort(fruits);
        Arrays.sort(fruits, String.CASE_INSENSITIVE_ORDER);

        System.out.println(Arrays.toString(fruits));
    }
}
