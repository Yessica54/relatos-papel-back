package com.unir.grupo_12.ms_books_catalogue.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.unir.grupo_12.ms_books_catalogue.model.Book;
import com.unir.grupo_12.ms_books_catalogue.repository.BookRepository;
import com.unir.grupo_12.ms_books_catalogue.services.utils.SearchCriteria;
import com.unir.grupo_12.ms_books_catalogue.services.utils.SearchOperation;
import com.unir.grupo_12.ms_books_catalogue.services.utils.SearchStatement;
import com.unir.grupo_12.ms_books_catalogue.util.EntityUpdater;
import com.unir.grupo_12.ms_books_catalogue.util.Status;

import lombok.AllArgsConstructor;


@Service
@AllArgsConstructor
public class BookService {

    @Autowired
    private final BookRepository bookRepository;

    public Page<Book> getMatchingBooks(String parm, String category, 
        String title, String ISBN, String author, Status status,
        Integer range, Integer availability, Double price, String currency, LocalDate publicationDate, Pageable pageable) {
        return bookRepository.findAll(search(parm, currency, currency, currency, currency, status, availability, availability, price, currency, publicationDate), pageable);
    }

    public Book getBook(Long id) {
		return bookRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Not Found"));
	}

    public Book save(Book book) {
		return bookRepository.save(book);
	}
    
    @Transactional
	public Book update(Long id, Book book) {
        Book target = bookRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Not Found"));
        EntityUpdater.updateNonNullFields(book, target);
        return bookRepository.save(target);
	}
    
    public Book delete(Long id) {
        Book bookFind = bookRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Not Found"));
		bookRepository.deleteById(id);
        return bookFind;
	}

    public static Specification<Book> search(String parm, String category, 
    String title, String ISBN, String author, Status status,
    Integer range, Integer availability, Double price, String currency, LocalDate publicationDate) {
        SearchCriteria<Book> spec = new SearchCriteria<>();

        if (category !=null && (!category.isBlank() || !category.isEmpty())) {
            spec.add(new SearchStatement(List.of("category"), category, SearchOperation.MATCH));
        }

        if (title !=null && (!title.isBlank() || !title.isEmpty())) {
            spec.add(new SearchStatement(List.of("title"), title, SearchOperation.MATCH));
        }

        if (ISBN !=null && (!ISBN.isBlank() || !ISBN.isEmpty())) {
            spec.add(new SearchStatement(List.of("ISBN"), ISBN, SearchOperation.EQUAL));
        }

        if (author !=null && (!author.isBlank() || !author.isEmpty())) {
            spec.add(new SearchStatement(List.of("author"), author, SearchOperation.MATCH));
        }

        if (status != null) {
            spec.add(new SearchStatement(List.of("status"), status, SearchOperation.MATCH));
        }

        if (range != null) {
            spec.add(new SearchStatement(List.of("range"), range, SearchOperation.MATCH));
        }

        if (availability != null) {
            spec.add(new SearchStatement(List.of("availability"), availability, SearchOperation.LESS_THAN_EQUAL));
        }

        if (price != null) {
            spec.add(new SearchStatement(List.of("price"), price, SearchOperation.GREATER_THAN_EQUAL));
        }

        if (currency !=null && (!currency.isBlank() || !currency.isEmpty())) {
            spec.add(new SearchStatement(List.of("currency"), currency, SearchOperation.MATCH));
        }

        if (publicationDate != null) {
            spec.add(new SearchStatement(List.of("publicationDate"), publicationDate, SearchOperation.LESS_THAN_EQUAL));
        }

        if (parm!= null && (!parm.isBlank() || !parm.isEmpty())) {
            spec.add(new SearchStatement(List.of("title", "category", "author", "ISBN"), parm, SearchOperation.MULTI_MATCH));
        }
        return spec;
    }

}
