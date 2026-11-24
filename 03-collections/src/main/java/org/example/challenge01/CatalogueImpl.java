package org.example.challenge01;

import org.example.model.Book;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class CatalogueImpl implements Catalogue {

    private final List<Book> books = new ArrayList<>();

    @Override
    public void addBook(Book book) {
         books.add(Objects.requireNonNull(book));
    }

    //  Read only
    @Override
    public List<Book> getAllBooks() {
        return Collections.unmodifiableList(books);
    }

    @Override
    public Book getBookByRowIndex(int index) { // indexOutOfBoundsException if index is invalid
        return books.get(index);
    }

    // possible : concurrentModificationException
    @Override
    public Boolean deleteBookByIsbn(String isbn) {
        return books.removeIf(  b -> b.isbn().equals(isbn) );
    }

    @Override
    public Book getFirst() {
        return books.getFirst();
    }

    @Override
    public Book getLast() {
        return books.getLast();
    }

    @Override
    public List<Book> enOrderInverse() {
        return List.copyOf(books.reversed());
    }
}
