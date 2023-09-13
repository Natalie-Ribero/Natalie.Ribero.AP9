package com.mindhub.homebanking;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.List;

@DataJpaTest

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
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

    @Autowired
    private CardRepository cardRepository;

    @Test
    public void existCards() {
        List<Card> cards = cardRepository.findAll();
        assertThat(cards, is(not(empty())));
    }
    @Autowired
    private ClientRepository clientRepository;

    @Test
    public void existClient() {
        List<Client> client = clientRepository.findAll();
        assertThat(client, is(not(empty())));
    }
    @Autowired
    private ClientLoanRepository clientLoanRepository;

    @Test
    public void existClientLoan() {
        List<ClientLoan> loan = clientLoanRepository.findAll();
        assertThat(loan, is(not(empty())));
    }
    @Autowired
    private TransactionRepository transactionRepository;

    @Test
    public void existTransaction() {
        List<Transaction> transaction = transactionRepository.findAll();
        assertThat(transaction, is(not(empty())));
    }

}

