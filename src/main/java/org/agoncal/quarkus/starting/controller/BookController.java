package org.agoncal.quarkus.starting.controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.agoncal.quarkus.starting.dto.BookDTO;
import org.agoncal.quarkus.starting.service.BookService;

import java.util.List;

@Path("/api/books")
public class BookController {
    @Inject
    private BookService bookService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<BookDTO> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public BookDTO getBook(@PathParam("id") Long id) {
        return bookService.getBook(id);
    }

    @GET
    @Path("/count")
    @Produces(MediaType.TEXT_PLAIN)
    public int countAllBooks() {
        return bookService.countAllBooks();
    }
}
