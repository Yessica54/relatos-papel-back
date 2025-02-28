package com.unir.grupo_12.ms_books_catalogue.model;

import java.time.LocalDate;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import com.unir.grupo_12.ms_books_catalogue.util.Status;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Document(indexName = "books", createIndex = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BookElasticsearch {

    @Id
    private String id;
    
    @Field(type = FieldType.Text, name = "title")
    private String title;

    @Field(type = FieldType.Keyword, name = "category") 
    private String category;

    @Field(type = FieldType.Text, name = "ISBN")
    private String ISBN;

    @Field(type = FieldType.Keyword, name = "status")
    private Status status;

    @Field(type = FieldType.Integer, name = "range")
    private int range;

    @Field(type = FieldType.Integer, name = "availability")
    private int availability;

    @Field(type = FieldType.Keyword, name = "cover")
    private String cover;

    @Field(type = FieldType.Double, name = "price")
    private double price;

    @Field(type = FieldType.Keyword, name = "currency")
    private String currency; 

    @Field(type = FieldType.Date, name = "publicationDate")
    private LocalDate publicationDate;

    @Field(type = FieldType.Text, name = "author")
    private String author;
}
