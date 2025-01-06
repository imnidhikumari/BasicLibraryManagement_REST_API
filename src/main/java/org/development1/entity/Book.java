package org.development1.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name="books")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name="title", nullable = false)
    @NotBlank
    String title;

    @Column(name="author", nullable = false)
    @NotBlank
    String author;

    @Column(name="isbn", unique=true, nullable = false)
    @NotBlank
    String isbn;     //International Standard Book Number
}
