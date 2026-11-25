package org.example.challenge01;

import org.example.model.Book;

import java.util.List;

public interface Catalogue {

    public void addBook(Book book);
    public List<Book> getAllBooks();
    public Book getBookByRowIndex(int index);
    public boolean deleteBookByIsbn(String isbn);
    public Book getFirst();
    public Book getLast();
    List<Book> enOrderInverse();


}
