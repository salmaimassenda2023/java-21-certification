package org.example.challenge03;

import org.example.model.Book;

import java.util.Optional;

public interface CatalogueIndexe {

    public boolean addBook(Book book);
    public void replaceBook(Book book);
    public Optional<Book> findBookByIsbn(String isbn);
    public boolean deleteBookByIsbn(String isbn);
    public int countBooks();
}
