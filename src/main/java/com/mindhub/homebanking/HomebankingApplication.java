package com.mindhub.homebanking;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
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
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository){
		return(args) -> {
			Client client1 = new Client("Melba", "Morel","melba@mindhub.com");
			clientRepository.save(client1);
            Client client2 = new Client("Susana", "Guerrero","sguerrero@mindhub.com");
			clientRepository.save(client2);
			Client client3 = new Client("Daniel", "Estebez","daniestebez@mindhub.com");
			clientRepository.save(client3);

			LocalDate today = LocalDate.now();
			LocalDate tomorrow = today.plusDays(1);

			Account account1 = new Account("VIN001",today,5000);
			accountRepository.save(account1);
		};
	}
}
