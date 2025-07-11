package br.edu.ifba.inf008.interfaces.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long bookId;

    private String title;

    private String author;

    private String isbn;

    @Column(name = "published_year")
    private int publishedYear;

    @Column(name = "copies_available")
    private int copiesAvailable;
}
