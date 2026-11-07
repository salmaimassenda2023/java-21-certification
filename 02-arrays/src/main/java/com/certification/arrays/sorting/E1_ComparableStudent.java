package com.certification.arrays.sorting;

import com.certification.arrays.model.Student;

import java.util.Arrays;

public class E1_ComparableStudent {
    public static void main(String[] args) {
        Student[] students = {
                new Student(1,"Ali",3.5),
                new Student(3,"Sara",3.9),
                new Student(2,"Omar",3.1),
        };
        Arrays.sort(students);
        for(Student student : students){
            System.out.println(student);
        }
    }
}
