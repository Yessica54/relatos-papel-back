package com.unir.grupo_12.ms_books_payments;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class MsBooksPaymentsApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsBooksPaymentsApplication.class, args);
	}

}