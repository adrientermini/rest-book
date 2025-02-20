package org.agoncal.quarkus.starting.repository;

import jakarta.enterprise.context.ApplicationScoped;
import org.agoncal.quarkus.starting.entity.Book;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class BookRepository {
    public List<Book> getAllBooks() {
        return Arrays.asList(
                new Book(1L, "Understanding Quarkus", "Antonio", 2020, "IT"),
                new Book(2L, "Practising Quarkus", "Antonio", 2020, "IT"),
                new Book(3L, "Effective Java", "Josh Bloch", 2001, "IT"),
                new Book(4L, "Thinking in Java", "Bruce Eckel", 1998, "IT")
        );
    }

    public Optional<Book> getBook(Long id) {
        return getAllBooks()
                .stream()
                .filter(book -> book.getId().equals(id))
                .findFirst();
    }

    public int countAllBooks() {
        return this.getAllBooks().size();
    }
}
