package com.unir.grupo_12.ms_books_catalogue.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AggregationDetails {
    private String key;
    private Integer count;
}
