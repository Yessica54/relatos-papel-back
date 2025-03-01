package com.unir.grupo_12.ms_books_catalogue.repository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.range.ParsedRange;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Repository;

import com.unir.grupo_12.ms_books_catalogue.dtos.AggregationDetails;
import com.unir.grupo_12.ms_books_catalogue.dtos.BooksQueryResponse;
import com.unir.grupo_12.ms_books_catalogue.model.BookElasticsearch;
import com.unir.grupo_12.ms_books_catalogue.util.Status;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Repository
@RequiredArgsConstructor
@Slf4j
public class DataAccessRepository {

    private final BookRepositoryElasticsearch bookRepository;
    private final ElasticsearchOperations elasticClient;

    public BookElasticsearch save(BookElasticsearch book) {
        return bookRepository.save(book);
    }

    public Boolean delete(BookElasticsearch book) {
        bookRepository.delete(book);
        return Boolean.TRUE;
    }

    public Optional<BookElasticsearch> findById(String id) {
        return bookRepository.findById(id);
    }

    @SneakyThrows
    public BooksQueryResponse findBooks(List<String> categoryValues,
            String title, String ISBN, String author, Status status,
            Integer range, Integer availability, List<String> priceValues, List<String> currencyValues,
            LocalDate publicationDate, int  page) {

        BoolQueryBuilder querySpec = QueryBuilders.boolQuery();
        
        if (categoryValues != null && !categoryValues.isEmpty()) {
            log.info("categoryValues %s", categoryValues.toString());
            categoryValues.forEach(
                    category -> querySpec.must(QueryBuilders.termQuery("category", category)));
        }

        if (!StringUtils.isEmpty(title)) {
            querySpec.must(QueryBuilders.matchQuery("title", title));
        }

        if (!StringUtils.isEmpty(ISBN)) {
            querySpec.must(QueryBuilders.matchQuery("ISBN", ISBN));
        }

        if (!StringUtils.isEmpty(author)) {
            querySpec.must(QueryBuilders.matchQuery("author", author));
        }
        if (range != null) {
            querySpec.must(QueryBuilders.termQuery("range", range));
        }
        if (availability != null) {
            querySpec.must(QueryBuilders.rangeQuery("availability").gte(availability));
        }
        if (priceValues != null && !priceValues.isEmpty()) {
            priceValues.forEach(
                    price -> {
                        String[] priceRange = price != null && price.contains("-") ? price.split("-") : new String[] {};

                        if (priceRange.length == 2) {
                            if ("".equals(priceRange[0])) {
                                querySpec.must(QueryBuilders.rangeQuery("price").to(priceRange[1]));
                            } else {
                                querySpec.must(QueryBuilders.rangeQuery("price").from(priceRange[0]).to(priceRange[1]));
                            }
                        }
                        if (priceRange.length == 1) {
                            querySpec.must(QueryBuilders.rangeQuery("price").from(priceRange[0]));
                        }
                    });
        }

        if (currencyValues != null && !currencyValues.isEmpty()) {
            currencyValues.forEach(
                    currency -> querySpec.must(QueryBuilders.termQuery("currency", currency)));
        }
        if (publicationDate != null) {
            querySpec.must(QueryBuilders.matchQuery("publicationDate", publicationDate));
        }
        if (!querySpec.hasClauses()) {
            querySpec.must(QueryBuilders.matchAllQuery());
        }

        querySpec.must(QueryBuilders.termQuery("status", Status.ACTIVE));
        log.info(querySpec.toString());
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder().withQuery(querySpec);

        nativeSearchQueryBuilder.addAggregation(AggregationBuilders.terms("Category Aggregation")
                .field("category")
                .size(1000));

        nativeSearchQueryBuilder.addAggregation(AggregationBuilders.terms("Currency Aggregation")
                .field("currency")
                .size(1000));

        nativeSearchQueryBuilder.addAggregation(AggregationBuilders.range("Price Aggregation")
                .field("price")
                .addRange("0-20", 0,20)
                .addRange("20-40", 20, 40)
                .addUnboundedTo("-40", 40));

        nativeSearchQueryBuilder.withMaxResults(5);
        Pageable pageable = PageRequest.of(page, 5);
        nativeSearchQueryBuilder.withPageable(pageable);

        

        Query query = nativeSearchQueryBuilder.build();
        SearchHits<BookElasticsearch> result = elasticClient.search(query, BookElasticsearch.class);

        return new BooksQueryResponse(getResponseBooks(result, pageable), getResponseAggregations(result));
        
    }

    private Page<BookElasticsearch> getResponseBooks(SearchHits<BookElasticsearch> result,Pageable pageable) {
        return new PageImpl<>(result.getSearchHits().stream()
        .map(SearchHit::getContent)
        .collect(Collectors.toList()), pageable, result.getTotalHits());
    }

    private Map<String, List<AggregationDetails>> getResponseAggregations(SearchHits<BookElasticsearch> result) {

        // Mapa de detalles de agregaciones
        Map<String, List<AggregationDetails>> responseAggregations = new HashMap<>();

        // Recorremos las agregaciones
        if (result.hasAggregations()) {
            Map<String, Aggregation> aggs = result.getAggregations().asMap();

            // Recorremos las agregaciones
            aggs.forEach((key, value) -> {

                // Si no existe la clave en el mapa, la creamos
                if (!responseAggregations.containsKey(key)) {
                    responseAggregations.put(key, new LinkedList<>());
                }

                // Si la agregacion es de tipo termino, recorremos los buckets
                if (value instanceof ParsedStringTerms parsedStringTerms) {
                    parsedStringTerms.getBuckets().forEach(bucket -> {
                        responseAggregations.get(key)
                                .add(new AggregationDetails(bucket.getKey().toString(), (int) bucket.getDocCount()));
                    });
                }

                // Si la agregacion es de tipo rango, recorremos tambien los buckets
                if (value instanceof ParsedRange parsedRange) {
                    parsedRange.getBuckets().forEach(bucket -> {
                        responseAggregations.get(key)
                                .add(new AggregationDetails(bucket.getKeyAsString(), (int) bucket.getDocCount()));
                    });
                }
            });
        }
        return responseAggregations;
    }

}
