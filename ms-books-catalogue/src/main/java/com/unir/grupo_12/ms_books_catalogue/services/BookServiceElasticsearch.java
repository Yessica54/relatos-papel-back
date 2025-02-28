package com.unir.grupo_12.ms_books_catalogue.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.unir.grupo_12.ms_books_catalogue.dtos.BooksQueryResponse;
import com.unir.grupo_12.ms_books_catalogue.dtos.CreateBookRequest;
import com.unir.grupo_12.ms_books_catalogue.model.BookElasticsearch;
import com.unir.grupo_12.ms_books_catalogue.repository.DataAccessRepository;
import com.unir.grupo_12.ms_books_catalogue.util.Status;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookServiceElasticsearch {

    private final DataAccessRepository repository;

    public BooksQueryResponse getBooks(List<String> categoryValues, String title, String ISBN, String author,
            Status status, Integer range, Integer availability, List<String> priceValues, List<String> currencyValues,
            LocalDate publicationDate, int page) {
        return repository.findBooks(categoryValues, title, ISBN, author, status, range, availability, priceValues,
                currencyValues, publicationDate, page);
    }

    public BookElasticsearch getBook(String bookId) {
        return repository.findById(bookId).orElse(null);
    }

    public Boolean removeBook(String bookId) {

        BookElasticsearch book = repository.findById(bookId).orElse(null);
        if (book != null) {
            repository.delete(book);
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }

    public BookElasticsearch createBook(CreateBookRequest request) {
        BookElasticsearch book = BookElasticsearch.builder().title(request.getTitle()).author(request.getAuthor())
                .ISBN(request.getISBN()).availability(request.getAvailability()).category(request.getCategory())
                .cover(request.getCover()).currency(request.getCurrency()).price(request.getPrice())
                .publicationDate(request.getPublicationDate()).range(request.getRange()).status(request.getStatus())
                .build();

        return repository.save(book);

    }
}
