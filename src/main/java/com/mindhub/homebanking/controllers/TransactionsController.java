package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class TransactionsController {
    @Autowired
    ClientRepository clientRepository;

    @Autowired
    AccountRepository accountRepository;

    @RequestMapping(path = "/transactions", method = RequestMethod.POST)
    public ResponseEntity<Object> transaction(

            @RequestParam Double amount, @RequestParam String description,
            @RequestParam String accountDestination, @RequestParam String accountSource,
            Authentication authentication) {

        Client clientAuthentication = clientRepository.findByEmail(authentication.getName());
        Set <Account> accountsClientAuthentication = clientAuthentication.getAccounts();

        if (amount.isNaN() || description.isEmpty() || accountDestination.isEmpty() || accountSource.isEmpty()) {
            return new ResponseEntity<Object>("Verifique de nuevo los datos", HttpStatus.FORBIDDEN);
        }
        if (accountSource.equals(accountDestination)) {
            return new ResponseEntity<Object>("Usted ingreso el mismo numero de cuenta origen y destino",
                    HttpStatus.FORBIDDEN);
        }
        if (accountRepository.findByNumber(accountSource) == null) {
            return new ResponseEntity<Object>("La cuenta de origen no existe",
                    HttpStatus.FORBIDDEN);
        }
        if (((accountRepository.findByNumber(accountSource)).getOwner().getId()) != clientAuthentication.getId()){
            return new ResponseEntity<Object>("La cuenta de origen no le pertenece",
                    HttpStatus.FORBIDDEN);
        }
        if (accountRepository.findByNumber(accountDestination) == null) {
            return new ResponseEntity<Object>("La cuenta destino no existe",
                    HttpStatus.FORBIDDEN);
        }
        if (accountRepository.findByNumber(accountSource).getBalance() >= amount) {
            return new ResponseEntity<Object>("No tiene los fondos suficientes",
                    HttpStatus.FORBIDDEN);
        }

        Transaction transaction1 = new Transaction(TransactionType.DEBIT, amount, description,
                LocalDate.now());
        Transaction transaction2 = new Transaction(TransactionType.CREDIT, -amount, description,
                LocalDate.now());

        return new ResponseEntity<Object>("Transaccion realizada", HttpStatus.CREATED);
    }

}
