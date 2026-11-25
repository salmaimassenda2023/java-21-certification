package org.example.challenge01;

import org.example.model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.NoSuchElementException;
import static org.junit.jupiter.api.Assertions.*;


class CatalogueImplTest {

    private final Book book1 = new Book("1", "A");
    private final Book book2 = new Book("2", "B");
    private final Book book3 = new Book("3", "C");

    private Catalogue catalogue;

    @BeforeEach
    void setUp() {
        catalogue = new CatalogueImpl();          // fresh, empty catalogue for every test
    }

    private void addThreeBooks() {
        catalogue.addBook(book1);
        catalogue.addBook(book2);
        catalogue.addBook(book3);
    }

    @Nested
    @DisplayName("addBook")
    class AddBook {
        @Test
        void normal_keepsInsertionOrder() {
            addThreeBooks();
            assertEquals(List.of(book1, book2, book3), catalogue.getAllBooks());
        }

        @Test
        void limit_acceptsDuplicates() {
            catalogue.addBook(book1);
            catalogue.addBook(book1);
            assertEquals(List.of(book1, book1), catalogue.getAllBooks());
        }

        @Test
        void error_rejectsNull() {
            assertThrows(NullPointerException.class, () -> catalogue.addBook(null));
        }
    }

    @Nested
    @DisplayName("getAllBooks")
    class GetAllBooks {
        @Test
        void limit_emptyCatalogueReturnsEmptyList() {
            assertTrue(catalogue.getAllBooks().isEmpty());
        }

        @Test
        void error_returnedListIsNotModifiable() {
            assertThrows(UnsupportedOperationException.class,
                    () -> catalogue.getAllBooks().add(book1));
        }
    }

    @Nested
    @DisplayName("getBookByRowIndex")
    class GetBookByRowIndex {
        @Test
        void normal_returnsBookAtIndex() {
            addThreeBooks();
            assertEquals(book2, catalogue.getBookByRowIndex(1));
        }

        @Test
        void limit_firstAndLastIndex() {
            addThreeBooks();
            assertEquals(book1, catalogue.getBookByRowIndex(0));
            assertEquals(book3, catalogue.getBookByRowIndex(2));
        }

        @Test
        void error_indexOutOfBounds() {
            addThreeBooks();
            assertThrows(IndexOutOfBoundsException.class, () -> catalogue.getBookByRowIndex(3));
            assertThrows(IndexOutOfBoundsException.class, () -> catalogue.getBookByRowIndex(-1));
        }
    }

    @Nested
    @DisplayName("deleteBookByIsbn")
    class DeleteBookByIsbn {
        @Test
        void normal_removesTheBook() {
            addThreeBooks();
            assertTrue(catalogue.deleteBookByIsbn("3"));
            assertEquals(List.of(book1, book2), catalogue.getAllBooks());
        }

        @Test
        void limit_removesAllCopies() {
            addThreeBooks();
            catalogue.addBook(book1);                       // second copy of book1
            assertTrue(catalogue.deleteBookByIsbn("1"));
            assertEquals(List.of(book2, book3), catalogue.getAllBooks());
        }

        @Test
        void limit_unknownIsbnReturnsFalseAndChangesNothing() {
            addThreeBooks();
            assertFalse(catalogue.deleteBookByIsbn("999"));
            assertEquals(3, catalogue.getAllBooks().size());
        }
    }

    @Nested
    @DisplayName("getFirst")
    class GetFirst {
        @Test
        void normal_returnsFirstBook() {
            addThreeBooks();
            assertEquals(book1, catalogue.getFirst());
        }

        @Test
        void limit_singleBookIsFirstAndLast() {
            catalogue.addBook(book2);
            assertEquals(book2, catalogue.getFirst());
        }

        @Test
        void error_emptyCatalogueThrows() {
            assertThrows(NoSuchElementException.class, () -> catalogue.getFirst());
        }
    }

    @Nested
    @DisplayName("getLast")
    class GetLast {
        @Test
        void normal_returnsLastBook() {
            addThreeBooks();
            assertEquals(book3, catalogue.getLast());
        }

        @Test
        void error_emptyCatalogueThrows() {
            assertThrows(NoSuchElementException.class, () -> catalogue.getLast());
        }
    }

    @Nested
    @DisplayName("enOrderInverse")
    class EnOrderInverse {
        @Test
        void normal_returnsReversedOrder() {
            addThreeBooks();
            assertEquals(List.of(book3, book2, book1), catalogue.enOrderInverse());
        }

        @Test
        void limit_emptyCatalogueReturnsEmptyList() {
            assertTrue(catalogue.enOrderInverse().isEmpty());
        }

        @Test
        void error_returnedListIsNotModifiable() {
            addThreeBooks();
            assertThrows(UnsupportedOperationException.class,
                    () -> catalogue.enOrderInverse().add(book1));
        }
    }
}