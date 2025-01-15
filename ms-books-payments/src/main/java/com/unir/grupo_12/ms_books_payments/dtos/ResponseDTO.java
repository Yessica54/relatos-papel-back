package com.unir.grupo_12.ms_books_payments.dtos;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseDTO {

    private HttpStatus code;
    private String message;
    
}
