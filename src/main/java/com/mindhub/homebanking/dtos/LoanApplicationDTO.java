package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.ClientLoan;

public class LoanApplicationDTO {
    private Long id;
    private int amount;
    private int payments;
    private String number;

    public LoanApplicationDTO(ClientLoan clientLoan, String number) {
        this.id = clientLoan.getLoan().getId();
        this.amount = clientLoan.getAmount();
        this.payments = clientLoan.getPayments();
        this.number = number;
    }
}
