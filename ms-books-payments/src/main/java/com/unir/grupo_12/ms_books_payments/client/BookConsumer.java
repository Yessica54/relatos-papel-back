package com.unir.grupo_12.ms_books_payments.client;

import org.springframework.http.ResponseEntity;

import com.unir.grupo_12.ms_books_payments.dtos.BookDTO;

public interface BookConsumer {
    public ResponseEntity<BookDTO> getBook(Long id);
}
