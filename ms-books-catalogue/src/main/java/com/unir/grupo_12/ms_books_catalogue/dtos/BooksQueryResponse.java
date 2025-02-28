package com.unir.grupo_12.ms_books_catalogue.dtos;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.unir.grupo_12.ms_books_catalogue.model.BookElasticsearch;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BooksQueryResponse {

    private Page<BookElasticsearch> books;
    private Map<String, List<AggregationDetails>> aggs;

}
