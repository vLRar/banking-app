package com.banking.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "transactions")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String date;
    private String description;
    private double amount;

    @Column(name = "source_iban")
    private String sourceIban;

    @Column(name = "destination_iban")
    private String destinationIban;


    public Transaction(String date, String description, double amount) {
        this.date = date;
        this.description = description;
        this.amount = amount;
    }
}