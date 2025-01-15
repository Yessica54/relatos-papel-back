package com.unir.grupo_12.ms_books_catalogue.services;

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
import com.unir.grupo_12.ms_books_catalogue.util.EntityUpdater;

import lombok.AllArgsConstructor;


@Service
@AllArgsConstructor
public class BookService {

    @Autowired
    private final BookRepository bookRepository;

    public Page<Book> getMatchingBooks(String parm, Pageable pageable) {
        if(parm == null || parm.isEmpty() || parm.isBlank()){
            return bookRepository.findAll(pageable);
        }
        return bookRepository.findAll(search(parm), pageable);
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

    public static Specification<Book> search(String value) {
        String search = "%" + value + "%";
        return (root, query, builder) -> builder.or(
                builder.like(builder.lower(root.get("title")), search.toLowerCase()),
                builder.like(builder.lower(root.get("category")), search.toLowerCase()),
                builder.like(builder.lower(root.get("ISBN")), search.toLowerCase()),
                builder.like(builder.lower(root.get("author")), search.toLowerCase())
        );
    }

}
