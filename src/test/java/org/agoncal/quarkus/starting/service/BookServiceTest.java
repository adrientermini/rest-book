package org.agoncal.quarkus.starting.service;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import org.agoncal.quarkus.starting.dto.BookDTO;
import org.agoncal.quarkus.starting.entity.Book;
import org.agoncal.quarkus.starting.repository.BookRepository;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
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

    @DisplayName(value = "Get book by id")
    @Test
    void getBook() {
        // Given
        Optional<Book> book = Optional.ofNullable(random.nextObject(Book.class));
        when(bookRepository.getBook(anyLong())).thenReturn(book);

        // When
        BookDTO result = bookService.getBook(anyLong());

        // Then
        assertNotNull(result);
    }

    @DisplayName(value = "Get book by id - Not found")
    @Test
    void getBook_NotFound() {
        // Given
        when(bookRepository.getBook(anyLong())).thenReturn(Optional.empty());

        // When
        NotFoundException exception = assertThrows(NotFoundException.class, () -> bookService.getBook(anyLong()));

        assertNotNull(exception);
        assertEquals("Book not found", exception.getMessage());
    }

    @DisplayName(value = "Count all books")
    @Test
    void countAllBooks() {
        // Given
        int count = random.nextInt();
        when(bookRepository.countAllBooks()).thenReturn(count);

        // When
        int result = bookService.countAllBooks();

        // Then
        assertEquals(count, result);
    }
}
