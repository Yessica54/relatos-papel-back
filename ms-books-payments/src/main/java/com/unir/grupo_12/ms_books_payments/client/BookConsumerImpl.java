package com.unir.grupo_12.ms_books_payments.client;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.unir.grupo_12.ms_books_payments.dtos.BookDTO;

@Service
public class BookConsumerImpl implements BookConsumer{

    private final RestTemplate restTemplate;

    public BookConsumerImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public ResponseEntity<BookDTO> getBook(Long id) {
        String endpoint = "http://ms-books-catalogue/book/{id}";
        ResponseEntity<BookDTO> response = restTemplate.getForEntity(endpoint, 
            BookDTO.class, id);
       
        return response;
    }

}
