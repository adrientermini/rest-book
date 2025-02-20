package org.agoncal.quarkus.starting.repository;

import jakarta.enterprise.context.ApplicationScoped;
import org.agoncal.quarkus.starting.entity.Book;
import org.jeasy.random.EasyRandom;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@ApplicationScoped
public class BookRepository {
    private static EasyRandom random = new EasyRandom();
    private static int bookAmount = 3;

    public List<Book> getAllBooks() {
        AtomicLong id = new AtomicLong(1L);
        return random.objects(Book.class, bookAmount)
                .peek(book -> book.setId(id.getAndIncrement()))
                .collect(Collectors.toList());
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
