package org.agoncal.quarkus.starting.service;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.agoncal.quarkus.starting.dto.BookDTO;
import org.agoncal.quarkus.starting.entity.Book;
import org.agoncal.quarkus.starting.repository.BookRepository;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@QuarkusTest
class BookServiceTest {
    private static EasyRandom random = new EasyRandom();

    @InjectMock
    private BookRepository bookRepository;

    @Inject
    private BookService bookService;

    @DisplayName(value = "Get All Books")
    @Test
    void getAllBooks() {
        // Given
        List<Book> books = random.objects(Book.class, 3).collect(Collectors.toList());
        when(bookRepository.getAllBooks()).thenReturn(books);

        // When
        List<BookDTO> result = bookService.getAllBooks();

        // Then
        assertEquals(books.size(), result.size());
    }
}
