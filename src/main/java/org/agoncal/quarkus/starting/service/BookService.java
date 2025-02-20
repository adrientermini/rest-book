package org.agoncal.quarkus.starting.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import org.agoncal.quarkus.starting.dto.BookDTO;
import org.agoncal.quarkus.starting.mapper.BookMapper;
import org.agoncal.quarkus.starting.repository.BookRepository;
import org.jboss.logging.Logger;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class BookService {
    @Inject
    private BookRepository bookRepository;

    @Inject
    private BookMapper bookMapper;

    @Inject
    private Logger logger;

    public List<BookDTO> getAllBooks() {
        logger.info("Getting all books");

        return bookRepository.getAllBooks()
                .stream()
                .map(bookMapper::mapToBookDto)
                .collect(Collectors.toList());
    }

    public BookDTO getBook(Long id) {
        logger.info("Getting book with id " + id);

        return bookRepository.getBook(id)
                .map(bookMapper::mapToBookDto)
                .orElseThrow(() -> new NotFoundException("Book not found"));
    }

    public int countAllBooks() {
        logger.info("Count books");

        return bookRepository.countAllBooks();
    }
}
