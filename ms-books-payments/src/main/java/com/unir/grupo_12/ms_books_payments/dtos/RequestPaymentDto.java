package com.unir.grupo_12.ms_books_payments.dtos;

import java.util.List;

import com.unir.grupo_12.ms_books_payments.util.PaymentType;
import com.unir.grupo_12.ms_books_payments.util.StatusPayment;

import lombok.Data;

@Data
public class RequestPaymentDto {

    private String name;
    private String lastName;
    private String andress;
    private double amount;
    private StatusPayment status;
    private PaymentType paymentType;
    private List<BookDTO> books;
}
