package com.certification.arrays.model;

public class Student {
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
}
