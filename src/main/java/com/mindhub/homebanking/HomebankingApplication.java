package com.mindhub.homebanking;

import com.mindhub.homebanking.models.Client;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
		Client client1 = new Client("Melba", "Morel", "Melba@mindhub.com");
	}

}
