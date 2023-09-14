package com.mindhub.homebanking;


import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;
import static org.hamcrest.Matchers.*;

@DataJpaTest

@AutoConfigureTestDatabase(replace = NONE)
public class RepositoriesTest {

    @Autowired
    private LoanRepository loanRepository;

    @Test
    public void existLoans() {
        List<Loan> loans = loanRepository.findAll();
        assertThat(loans, is(not(empty())));
    }

    @Test
    public void existPersonalLoan() {
        List<Loan> loans = loanRepository.findAll();
        assertThat(loans, hasItem(hasProperty("name", is("Personal"))));
    }

    @Autowired
    private AccountRepository accountRepository;

    @Test
    public void existAccounts() {
        List<Account> account = accountRepository.findAll();
        assertThat(account, is(not(empty())));
    }
    @Test
    public void existAccount() {
        List<Account> account = accountRepository.findAll();
        assertThat(account, is(not(empty())));
    }

    @Autowired
    private CardRepository cardRepository;

    @Test
    public void existCards() {
        List<Card> cards = cardRepository.findAll();
        assertThat(cards, is(not(empty())));
    }

    @Test
    public void haveMoreThreeCards(){
        List<Client> clients = clientRepository.findAll();
        for (Client client : clients){
            assertThat(client.getCards(), hasSize(lessThanOrEqualTo(3)));
        }
    }
    @Autowired
    private ClientRepository clientRepository;

    @Test
    public void existClient() {
        List<Client> clients = clientRepository.findAll();
        assertThat(clients, is(not(empty())));
    }

    @Test
    public void haveMoreThreeAccounts(){
        List<Client> clients = clientRepository.findAll();
        for (Client client : clients){
            assertThat(client.getAccounts(), hasSize(lessThanOrEqualTo(3)));
        }
    }

    @Test
    public void existClientMelba() {
        List<Client> clients = clientRepository.findAll();
        assertThat(clients, hasItem(hasProperty("firstName", is("Melba"))));
    }

    @Autowired
    private TransactionRepository transactionRepository;

    @Test
    public void existTransaction() {
        List<Transaction> transaction = transactionRepository.findAll();
        assertThat(transaction, is(not(empty())));
    }

}

