package org.agoncal.quarkus.starting.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Book {
    private Long id;
    private String title;
    private String author;
    private int yearOfPublication;
    private String genre;
}
