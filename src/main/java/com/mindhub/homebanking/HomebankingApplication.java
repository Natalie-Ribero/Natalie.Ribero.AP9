package com.mindhub.homebanking;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}
	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository, TransactionRepository transactionRepository){
		return(args) -> {
			Client client1 = new Client("Melba", "Morel","melba@mindhub.com");
            Client client2 = new Client("Susana", "Guerrero","sguerrero@mindhub.com");

			Account account1 = new Account("VIN001",LocalDate.now(),5000);
			Account account2 = new Account("VIN002",LocalDate.now().plusDays(1),7500);
			Account account3 = new Account("VIN003",LocalDate.now(),8500);

			Transaction transaction1 = new Transaction(account1, TransactionType.CREDIT, 5000.00, "ALQUILER", LocalDate.now());
			Transaction transaction2 = new Transaction(account2, TransactionType.DEBIT, -52000.00, "Pago hipoteca", LocalDate.now());
			Transaction transaction3 = new Transaction(account3, TransactionType.CREDIT, 2000.00, "salario", LocalDate.now());

			client1.addAccount(account1);
			client1.addAccount(account2);
			client2.addAccount(account3);

			account1.addTransaction(transaction1);
			account1.addTransaction(transaction2);
			account1.addTransaction(transaction3);

			clientRepository.save(client2);
			clientRepository.save(client1);

			accountRepository.save(account1);
			accountRepository.save(account2);
			accountRepository.save(account3);

			transactionRepository.save(transaction3);
			transactionRepository.save(transaction1);
			transactionRepository.save(transaction2);












			
		};
	}
}
