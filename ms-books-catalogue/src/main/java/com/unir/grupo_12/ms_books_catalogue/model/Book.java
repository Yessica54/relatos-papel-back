package com.unir.grupo_12.ms_books_catalogue.model;

import java.io.Serializable;
import java.time.LocalDate;

import org.hibernate.annotations.DynamicUpdate;

import com.unir.grupo_12.ms_books_catalogue.util.Status;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@DynamicUpdate
@Table(name = "books")
@Data
public class Book implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String category;
    private String ISBN;
    
    @Enumerated(EnumType.STRING)
    private Status status;
    private int range;
    private int availability;
    private String cover;
    private double price;
    private String currency;
    private LocalDate publicationDate;
    private String author;
}
