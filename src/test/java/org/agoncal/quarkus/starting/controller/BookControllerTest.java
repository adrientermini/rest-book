package org.agoncal.quarkus.starting.controller;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;
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
                .body("$.size()", is(3));
    }
}
