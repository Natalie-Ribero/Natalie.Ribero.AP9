package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="account_id")
    private Account accountOfTransaction;

    private TransactionType type;
    private Double amount;
    private String description;
    private LocalDate date;

    public Transaction(){}

    public Transaction(Account accountOfTransaction, TransactionType type, Double amount, String description, LocalDate date) {
        this.accountOfTransaction = accountOfTransaction;
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.date = date;
    }
}
