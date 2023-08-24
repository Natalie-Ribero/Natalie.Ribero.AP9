package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="client_id")
    private Client owner;

    @OneToMany(mappedBy = "account", fetch = FetchType.EAGER)
    private Set<Transaction> transactions = new HashSet<>();

    private String number;
    private LocalDate creationDate;
    private double balance;

    public Account() {}

    public Account(String number, LocalDate creationDate, double balance) {
        this.number = number;
        this.creationDate = creationDate;
        this.balance = balance;
    }

    public long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public double getBalance() {
        return balance;
    }

    public Client getOwner() {
        return owner;
    }

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setOwner(Client owner) {
        this.owner = owner;
    }

    public void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
    }

    public void addTransaction(Transaction transaction) {
        transaction.setAccount(this);
        transactions.add(transaction);
    }

    public static String createNumberAccount() {
        Random random = new Random();
        StringBuilder createString = new StringBuilder();
        createString.append("VIN-");
            int numberAccount = random.nextInt(90000000) + 1;
            createString.append(String.format("%08d", numberAccount));
        return createString.toString();
    }
}
