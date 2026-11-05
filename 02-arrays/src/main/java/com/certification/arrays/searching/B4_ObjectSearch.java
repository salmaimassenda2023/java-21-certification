package com.certification.arrays.searching;

import com.certification.arrays.model.Student;
import java.util.Arrays;
import java.util.Comparator;

public class B4_ObjectSearch {

    // Règle : même comparator pour sort et binarySearch.
    public static void main(String[] args) {
        Student[] s = {
                new Student(3, "Omar", 3.1),
                new Student(1, "Ali", 3.5),
                new Student(2, "Sara", 3.9)
        };

        Comparator<Student> byName = Comparator.comparing(Student::getName);
        Comparator<Student> byId = Comparator.comparingInt(Student::getId);

        Arrays.sort(s, byName);

        // Search
        Student key = new Student(2, "Sara", 0);
        int result = Arrays.binarySearch(s,key,byId);
        System.out.println(result);
    }
}
