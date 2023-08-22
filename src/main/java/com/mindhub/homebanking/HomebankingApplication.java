package com.mindhub.homebanking;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public class HomebankingApplication {

    public static void main(String[] args) {
        SpringApplication.run(HomebankingApplication.class, args);
    }

    @Bean
    public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository,
                                      TransactionRepository transactionRepository, LoanRepository loanRepository,
                                      ClientLoanRepository clientLoanRepository, CardRepository cardRepository) {
        return (args) -> {
            Client client1 = new Client("Melba", "Morel", "melba@mindhub.com");
            Client client2 = new Client("Susana", "Guerrero", "sguerrero@mindhub.com");

            Account account1 = new Account("VIN001", LocalDate.now(), 5000);
            Account account2 = new Account("VIN002", LocalDate.now().plusDays(1), 7500);
            Account account3 = new Account("VIN003", LocalDate.now(), 8500);

            Transaction transaction1 = new Transaction(account1, TransactionType.CREDIT, 5000.00, "ALQUILER",
                    LocalDate.now());
            Transaction transaction2 = new Transaction(account2, TransactionType.DEBIT, -52000.00, "Pago hipoteca",
                    LocalDate.now());
            Transaction transaction3 = new Transaction(account3, TransactionType.CREDIT, 2000.00, "salario",
                    LocalDate.now());

            Loan loan1 = new Loan("Hipotecario", 500000, List.of(12, 24, 36, 48, 60));
            Loan loan2 = new Loan("Personal", 100000, List.of(6, 12, 24));
            Loan loan3 = new Loan("Automotriz", 300000, List.of(6, 12, 24, 36));

            ClientLoan clientLoan1 = new ClientLoan(client1, loan1, 60, 400000);
            ClientLoan clientLoan2 = new ClientLoan(client1, loan2, 12, 50000);
            ClientLoan clientLoan3 = new ClientLoan(client2, loan2, 24, 100000);
            ClientLoan clientLoan4 = new ClientLoan(client2, loan3, 36, 200000);

            Card card1 = new Card(client1.toString(), CardType.DEBIT, CardColor.GOLD, Card.createNumberCard(), Card.createCvv(),
                    LocalDate.now(), LocalDate.now().plusYears(5));
            Card card2 = new Card(client1.toString(), CardType.CREDIT, CardColor.TITANIUM, Card.createNumberCard(), Card.createCvv(),
                    LocalDate.now(), LocalDate.now().plusYears(5));
            Card card3 = new Card(client2.toString(), CardType.CREDIT, CardColor.SILVER, Card.createNumberCard(), Card.createCvv(),
                    LocalDate.now(), LocalDate.now().plusYears(5));

            account1.addTransaction(transaction1);
            account2.addTransaction(transaction2);
            account3.addTransaction(transaction3);

            client1.addAccount(account1);
            client1.addAccount(account2);
            client2.addAccount(account3);

            client1.addLoan(clientLoan1);
            client1.addLoan(clientLoan2);
            client2.addLoan(clientLoan3);
            client2.addLoan(clientLoan4);

            loan1.addClient(clientLoan1);
            loan2.addClient(clientLoan2);
            loan2.addClient(clientLoan3);
            loan3.addClient(clientLoan4);

            client1.addCard(card1);
            client1.addCard(card2);
            client2.addCard(card3);

            clientRepository.save(client1);
            clientRepository.save(client2);

            accountRepository.save(account1);
            accountRepository.save(account2);
            accountRepository.save(account3);

            transactionRepository.save(transaction3);
            transactionRepository.save(transaction1);
            transactionRepository.save(transaction2);

            loanRepository.save(loan1);
            loanRepository.save(loan2);
            loanRepository.save(loan3);

            clientLoanRepository.save(clientLoan1);
            clientLoanRepository.save(clientLoan2);
            clientLoanRepository.save(clientLoan3);
            clientLoanRepository.save(clientLoan4);

            cardRepository.save(card1);
            cardRepository.save(card2);
            cardRepository.save(card3);

        };
    }
}
