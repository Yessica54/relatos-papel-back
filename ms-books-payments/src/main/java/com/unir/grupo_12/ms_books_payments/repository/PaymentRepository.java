package com.unir.grupo_12.ms_books_payments.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unir.grupo_12.ms_books_payments.model.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long>{

}
