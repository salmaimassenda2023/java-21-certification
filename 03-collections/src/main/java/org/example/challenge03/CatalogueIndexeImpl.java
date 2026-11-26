package org.example.challenge03;

import org.example.model.Book;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class CatalogueIndexeImpl implements  CatalogueIndexe{
    private  final Map<String,Book> books = new HashMap<>();

    @Override
    public boolean addBook(Book book) {
        Objects.requireNonNull(book, "book must not be null");
        return (books.putIfAbsent(book.isbn(),book) == null) ;
    }

    @Override
    public void replaceBook(Book book) {
        Objects.requireNonNull(book, "book must not be null");
        books.put(book.isbn(),book);
    }

    @Override
    public Optional<Book> findBookByIsbn(String isbn) {
        Objects.requireNonNull(isbn, "isbn must not be null");
        return Optional.ofNullable(books.get(isbn));
    }

    @Override
    public boolean deleteBookByIsbn(String isbn) {
        return ( books.remove(isbn) != null);
    }

    @Override
    public int countBooks() {
        return books.size();
    }
}
