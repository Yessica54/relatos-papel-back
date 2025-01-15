package com.unir.grupo_12.ms_books_catalogue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class MsBooksCatalogueApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsBooksCatalogueApplication.class, args);
	}

}
