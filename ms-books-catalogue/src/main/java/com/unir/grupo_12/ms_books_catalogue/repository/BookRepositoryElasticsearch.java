package com.unir.grupo_12.ms_books_catalogue.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.unir.grupo_12.ms_books_catalogue.model.BookElasticsearch;

public interface BookRepositoryElasticsearch extends ElasticsearchRepository<BookElasticsearch, String> {

    List<BookElasticsearch> findByTitle(String title);
	
	Optional<BookElasticsearch> findById(String id);
	
	BookElasticsearch save(BookElasticsearch book);
	
	void delete(BookElasticsearch book);
	
	List<BookElasticsearch> findAll();
    
}
