package com.certification.arrays.model;

public class Student implements Comparable<Student> {
    private final int id;
    private final String name;
    private final double gpa;
    //constructor
    public Student(int id, String name, double gpa) {
        this.id = id;
        this.name = name;
        this.gpa = gpa;
    }
    //getters
    public int getId() { return id; }
    public String getName() { return name; }
    public double getGpa() { return gpa; }
    //toString
    @Override public String toString() { return id + ":" + name + ":" + gpa; }

    // sort by id
    @Override
    public int compareTo(Student o) {
        return Integer.compare(this.id,o.id);
    }
}
