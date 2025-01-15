package com.unir.grupo_12.ms_books_payments.model;

import java.io.Serializable;

import org.hibernate.annotations.DynamicUpdate;

import com.unir.grupo_12.ms_books_payments.util.PaymentType;
import com.unir.grupo_12.ms_books_payments.util.StatusPayment;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@DynamicUpdate
@Table(name = "payment")
@Data
public class Payment implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String lastName;
    private String andress;
    private double amount;
    private int quantity;
    private StatusPayment status;
    private PaymentType paymentType;

}
