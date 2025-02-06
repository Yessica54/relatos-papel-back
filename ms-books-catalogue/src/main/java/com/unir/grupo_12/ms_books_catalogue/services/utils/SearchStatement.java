package com.unir.grupo_12.ms_books_catalogue.services.utils;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class SearchStatement {

    private List<String> keys;
    private Object value;
    private SearchOperation operation;
}