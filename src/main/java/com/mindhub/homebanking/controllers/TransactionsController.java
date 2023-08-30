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

            @RequestParam Double amount, @RequestParam String description,
            @RequestParam String accountDestination, @RequestParam String accountSource,
            Authentication authentication) {

        Client clientAuthentication = clientRepository.findByEmail(authentication.getName());
        Account accountDe = accountRepository.findByNumber(accountDestination);
        Account accountOr = accountRepository.findByNumber(accountSource);

        if (amount.isNaN() || description.isEmpty() || accountDestination.isEmpty() || accountSource.isEmpty()) {
            return new ResponseEntity<Object>("Verifique de nuevo los datos", HttpStatus.FORBIDDEN);
        }
        if (accountSource.equals(accountDestination)) {
            return new ResponseEntity<Object>("Usted ingreso el mismo numero de cuenta origen y destino",
                    HttpStatus.FORBIDDEN);
        }
        if (accountOr == null) {
            return new ResponseEntity<Object>("La cuenta de origen no existe",
                    HttpStatus.FORBIDDEN);
        }
        if ((accountOr.getOwner().getId()) != clientAuthentication.getId()){
            return new ResponseEntity<Object>("La cuenta de origen no le pertenece",
                    HttpStatus.FORBIDDEN);
        }
        if (accountDe == null) {
            return new ResponseEntity<Object>("La cuenta destino no existe",
                    HttpStatus.FORBIDDEN);
        }
        if (accountOr.getBalance() >= amount) {
            return new ResponseEntity<Object>("No tiene los fondos suficientes",
                    HttpStatus.FORBIDDEN);
        }

        Transaction transactionSource = new Transaction(TransactionType.DEBIT, amount, description + accountDestination,
                LocalDate.now());
        Transaction transactionDestination = new Transaction(TransactionType.CREDIT, -amount, description + accountSource,
                LocalDate.now());
        accountOr.addTransaction(transactionSource);
        accountDe.addTransaction(transactionDestination);
        transactionSource.addAccount(accountOr);
        transactionDestination.addAccount(accountDe);

        accountRepository.save(accountOr);
        accountRepository.save(accountDe);
        transactionRepository.save(transactionSource);
        transactionRepository.save(transactionDestination);

        return new ResponseEntity<Object>("Transaccion realizada", HttpStatus.CREATED);
    }

}
