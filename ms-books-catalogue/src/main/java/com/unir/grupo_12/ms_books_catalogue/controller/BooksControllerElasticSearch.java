package com.unir.grupo_12.ms_books_catalogue.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.unir.grupo_12.ms_books_catalogue.dtos.BooksQueryResponse;
import com.unir.grupo_12.ms_books_catalogue.dtos.CreateBookRequest;
import com.unir.grupo_12.ms_books_catalogue.model.BookElasticsearch;
import com.unir.grupo_12.ms_books_catalogue.services.BookServiceElasticsearch;
import com.unir.grupo_12.ms_books_catalogue.util.Status;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class BooksControllerElasticSearch {

    private final BookServiceElasticsearch service;

    @GetMapping("/books")
    public ResponseEntity<BooksQueryResponse> getBooks(
            @Param("categories") @RequestParam(required = false) List<String> categoryValues,
            @Param("title") @RequestParam(required = false) String title,
            @Param("ISBN") @RequestParam(required = false) String ISBN,
            @Param("author") @RequestParam(required = false) String author,
            @Param("status") @RequestParam(required = false) Status status,
            @Param("range") @RequestParam(required = false) Integer range,
            @Param("availability") @RequestParam(required = false) Integer availability,
            @Param("range_price") @RequestParam(required = false) List<String> priceValues,
            @Param("currencies") @RequestParam(required = false) List<String> currencyValues,
            @Param("publicationDate") @RequestParam(required = false) LocalDate publicationDate,
            @Param("page") @RequestParam(required = false, defaultValue = "0") int page) {

        BooksQueryResponse products = service.getBooks(categoryValues, title, ISBN, author, status, range, availability,
                priceValues, currencyValues, publicationDate, page);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/books/{bookId}")
    public ResponseEntity<BookElasticsearch> getBook(@PathVariable String bookId) {

        BookElasticsearch book = service.getBook(bookId);

        if (book != null) {
            return ResponseEntity.ok(book);
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @DeleteMapping("/books/{bookId}")
    public ResponseEntity<Void> deleteBook(@PathVariable String bookId) {

        Boolean removed = service.removeBook(bookId);

        if (Boolean.TRUE.equals(removed)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @PostMapping("/books")
    public ResponseEntity<BookElasticsearch> getBook(@RequestBody CreateBookRequest request) {

        BookElasticsearch created = service.createBook(request);

        if (created != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } else {
            return ResponseEntity.badRequest().build();
        }

    }
}
