package com.unir.grupo_12.ms_books_payments.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unir.grupo_12.ms_books_payments.dtos.RequestPaymentDto;
import com.unir.grupo_12.ms_books_payments.dtos.ResponseDTO;
import com.unir.grupo_12.ms_books_payments.services.PaymentService;

@RestController
@RequestMapping(value = "/payment", produces = MediaType.APPLICATION_JSON_VALUE)
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping
    public ResponseEntity<ResponseDTO> savePayment(@RequestBody RequestPaymentDto request) {
        return new ResponseEntity<>(paymentService.save(request), HttpStatus.CREATED);
    }
}