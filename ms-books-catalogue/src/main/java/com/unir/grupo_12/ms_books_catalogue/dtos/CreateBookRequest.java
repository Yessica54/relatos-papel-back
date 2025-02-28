package com.unir.grupo_12.ms_books_catalogue.dtos;

import java.time.LocalDate;

import com.unir.grupo_12.ms_books_catalogue.util.Status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateBookRequest {
    
    private String id;
    private String title;
    private String category;
    private String ISBN;
    private Status status;
    private int range;
    private int availability;
    private String cover;
    private double price;
    private String currency;
    private LocalDate publicationDate;
    private String author;
}
