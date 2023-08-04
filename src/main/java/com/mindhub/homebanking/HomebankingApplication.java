package com.mindhub.homebanking;

import com.mindhub.homebanking.models.Client;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}
	@Bean
	public CommandLineRunner initData(){
		return(args) -> {
			Client client1 = new Client("Melba", "Morel","melba@mindhub.com");
		};

	}
}
