package org.example;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        Book book = new Book("978-1234567890", "Clean Code");

        System.out.println(book.isbn());
        System.out.println(book.title());
        System.out.println(book);
    }
}