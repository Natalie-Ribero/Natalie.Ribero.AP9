package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
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

    @Autowired
    TransactionRepository transactionRepository;


    @Transactional
    @RequestMapping(path = "/transactions", method = RequestMethod.POST)
    public ResponseEntity<Object> transaction(


            @RequestParam String fromAccountNumber, @RequestParam String toAccountNumber, @RequestParam Double amount,
            @RequestParam String description, Authentication authentication) {

        if (amount.isNaN() || description.isEmpty() || toAccountNumber.isEmpty() || fromAccountNumber.isEmpty()) {
            return new ResponseEntity<Object>("Verifique de nuevo los datos", HttpStatus.FORBIDDEN);
        }
        if (amount < 1) {
            return new ResponseEntity<Object>("El monto no puede ser menor a 1", HttpStatus.FORBIDDEN);
        }

        if (fromAccountNumber.equals(toAccountNumber)) {
            return new ResponseEntity<Object>("Usted ingreso el mismo numero de cuenta origen y destino",
                    HttpStatus.FORBIDDEN);
        }

        Client clientAuthentication = clientRepository.findByEmail(authentication.getName());
        Account accountTo = accountRepository.findByNumber(toAccountNumber);
        Account accountFrom = accountRepository.findByNumber(fromAccountNumber);

        if (accountFrom == null) {
            return new ResponseEntity<Object>("La cuenta de origen no existe",
                    HttpStatus.FORBIDDEN);
        }
        if ((accountFrom.getOwner().getId()) != clientAuthentication.getId()) {
            return new ResponseEntity<Object>("La cuenta de origen no le pertenece",
                    HttpStatus.FORBIDDEN);
        }
        if (accountTo == null) {
            return new ResponseEntity<Object>("La cuenta destino no existe",
                    HttpStatus.FORBIDDEN);
        }
        if (accountFrom.getBalance() <= amount) {
            return new ResponseEntity<Object>("No tiene los fondos suficientes",
                    HttpStatus.FORBIDDEN);
        }

        Transaction transactionSource = new Transaction(TransactionType.DEBIT, -amount, description + " " + toAccountNumber,
                LocalDate.now());
        accountFrom.setBalance((accountFrom.getBalance()) - amount);
        Transaction transactionDestination = new Transaction(TransactionType.CREDIT, amount,
                description + " " + fromAccountNumber,
                LocalDate.now());
        accountTo.setBalance((accountTo.getBalance()) + amount);

        accountFrom.addTransaction(transactionSource);
        accountTo.addTransaction(transactionDestination);
        transactionSource.addAccount(accountFrom);
        transactionDestination.addAccount(accountTo);

        accountRepository.save(accountFrom);
        accountRepository.save(accountTo);
        transactionRepository.save(transactionSource);
        transactionRepository.save(transactionDestination);

        return new ResponseEntity<Object>("Transaccion realizada", HttpStatus.CREATED);
    }

}
