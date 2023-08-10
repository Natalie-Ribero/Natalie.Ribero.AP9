package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.ClientLoan;
import com.mindhub.homebanking.models.Loan;

public class ClientLoanDTO {
    private long id;
    private long loanId;
    private String name;
    private int payments;
    private int amount;

    public ClientLoanDTO(ClientLoan clientLoan) {
        this.id = clientLoan.getId();
        this.payments = clientLoan.getPayments();
        this.amount = clientLoan.getAmount();

    }

    public ClientLoanDTO(Loan loan) {
        this.loanId = loan.getId();
        this.name = loan.getName();
    }

    public long getId() {
        return id;
    }

    public int getPayments() {
        return payments;
    }

    public int getAmount() {
        return amount;
    }

    public long getLoanId() {
        return loanId;
    }

    public String getName() {
        return name;
    }

}
