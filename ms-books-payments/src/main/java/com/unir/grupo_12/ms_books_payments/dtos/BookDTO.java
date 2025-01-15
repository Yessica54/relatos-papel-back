package com.unir.grupo_12.ms_books_payments.dtos;

import java.time.LocalDate;

import com.unir.grupo_12.ms_books_payments.util.StatusBook;

import lombok.Data;

@Data
public class BookDTO {
    private Long id;
    private String title;
    private String category;
    private String ISBN;
    private StatusBook status;
    private int range;
    private int availability;
    private String cover;
    private double price;
    private String currency;
    private LocalDate publicationDate;
    private String author;
    private Integer quantity;


    public boolean isValid(){
        return this.status == StatusBook.ACTIVE && this.availability >= this.quantity;
    }
}
