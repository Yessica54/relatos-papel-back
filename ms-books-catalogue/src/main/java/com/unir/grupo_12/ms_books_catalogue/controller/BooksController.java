package com.unir.grupo_12.ms_books_catalogue.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.unir.grupo_12.ms_books_catalogue.model.Book;
import com.unir.grupo_12.ms_books_catalogue.services.BookService;
import com.unir.grupo_12.ms_books_catalogue.util.Status;



@RestController
@RequestMapping(value = "/book", produces = MediaType.APPLICATION_JSON_VALUE)
public class BooksController {

    @Autowired
    private  BookService bookService;
    
    @GetMapping()
    public HttpEntity<Page<Book>> getBooks(@Param("parm") @RequestParam(required = false) String parm, @Param("category") @RequestParam(required = false) String category, 
        @Param("title") @RequestParam(required = false) String title, @Param("ISBN") @RequestParam(required = false) String ISBN, @Param("author") @RequestParam(required = false) String author,  @Param("status") @RequestParam(required = false) Status status,
        @Param("range") @RequestParam(required = false) Integer range, @Param("availability") @RequestParam(required = false) Integer availability, @Param("price") @RequestParam(required = false) Double price,
        @Param("currency") @RequestParam(required = false) String currency,@Param("publicationDate") @RequestParam(required = false) LocalDate publicationDate,
        @SortDefault(sort = "id") @PageableDefault(size = 100) final Pageable pageable) {
        return new ResponseEntity<>(bookService.getMatchingBooks(parm, currency, currency, currency, currency, status, availability, availability, price, currency, publicationDate, pageable), HttpStatus.OK);
    }

    @GetMapping("/{id}")
	public ResponseEntity<Book> getBook(@PathVariable("id") Long id) {
        return new ResponseEntity<>(bookService.getBook(id), HttpStatus.OK);
       
	}

    @PostMapping("/create")
    public ResponseEntity<Book> saveBook(@RequestBody Book book) {
        return new ResponseEntity<>(bookService.save(book), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Book> update(@PathVariable("id") Long id, @RequestBody Book book) {
        return new ResponseEntity<>( bookService.update(id, book), HttpStatus.OK);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<Book> patch(@PathVariable("id") Long id, @RequestBody Book book) {
        return new ResponseEntity<>( bookService.update(id, book), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Book> delete(@PathVariable("id") Long id) {
        return new ResponseEntity<>(bookService.delete(id), HttpStatus.OK);
    }
}
