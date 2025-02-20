package org.agoncal.quarkus.starting.controller;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;
import static jakarta.ws.rs.core.MediaType.TEXT_PLAIN;
import static jakarta.ws.rs.core.Response.Status.OK;
import static org.apache.http.HttpHeaders.ACCEPT;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class BookControllerTest {

    @DisplayName(value = "Get All Books")
    @Test
    void getAllBooks() {
        given()
                .header(ACCEPT, APPLICATION_JSON)
        .when()
                .get("/api/books")
        .then()
                .statusCode(OK.getStatusCode())
                .body("size()", is(4));
    }

    @DisplayName(value = "Get book by id")
    @Test
    public void getBook() {
        given()
                .header(ACCEPT, APPLICATION_JSON)
                .pathParam("id", 1)
        .when()
                .get("/api/books/{id}")
        .then()
                .statusCode(OK.getStatusCode())
                .body("title", is("Understanding Quarkus"))
                .body("author", is("Antonio"));
    }

    @DisplayName(value = "Count all books")
    @Test
    public void countAllBooks() {
        given()
                .header(ACCEPT, TEXT_PLAIN)
        .when()
                .get("/api/books/count")
        .then()
                .statusCode(OK.getStatusCode())
                .body(is("4"));
    }
}
