package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.ClientLoan;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class ClientDTO {
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private Set<AccountDTO> accounts = new HashSet<>();
    private Set<ClientLoanDTO> loans = new HashSet<>();
    private Set<CardDTO> cards = new HashSet<>();

    public ClientDTO(Client client){
        this.id = client.getId();
        this.firstName = client.getFirstName();
        this.lastName = client.getLastName();
        this.email = client.getEmail();
        this.accounts = client
                .getAccounts()
                .stream()
                .map(AccountDTO::new)
                .collect(Collectors.toSet());
        this.loans = client
                .getClientLoans()
                .stream()
                .map(ClientLoanDTO::new)
                .collect(Collectors.toSet());
        this.cards = client.
                getCards()
                .stream()
                .map(CardDTO::new)
                .collect(Collectors.toSet());
    }
    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public Set<AccountDTO> getAccounts() {
        return accounts;
    }

    public Set<ClientLoanDTO> getloans() {
        return loans;
    }

    public Set<CardDTO> getCards() {
        return cards;
    }
}
