package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
public class ClientLoan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "loan_id")
    private Loan loan;

    private int payments;
    private int amount;

    public ClientLoan() {
    }

    public ClientLoan(int payments, int amount) {
        this.payments = payments;
        this.amount = amount;
    }

    public Client getClient() {
        return client;
    }

    public int getPayments() {
        return payments;
    }

    public int getAmount() {
        return amount;
    }

    public Loan getLoan() {
        return loan;
    }

    public long getId() {
        return id;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setPayments(int payments) {
        this.payments = payments;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setLoan(Loan loan) {
        this.loan = loan;
    }

    public void addLoanAndClient(Loan loanOwner,Client clientOwner) {
        loan = loanOwner;
        client = clientOwner;
    }
}
