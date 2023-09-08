package com.mindhub.homebanking.services.implement;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import com.mindhub.homebanking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class TransactionServiceImplement implements TransactionService {
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public ResponseEntity<Object> transaction(String fromAccountNumber, String toAccountNumber, Double amount, String description, Authentication authentication) {
        if (amount.isNaN() || description.isBlank() || toAccountNumber.isBlank() || fromAccountNumber.isBlank()) {
            return new ResponseEntity<Object>("Double-check the data", HttpStatus.FORBIDDEN);
        }
        if (amount < 1) {
            return new ResponseEntity<Object>("The amount cannot be less than 1", HttpStatus.FORBIDDEN);
        }

        if (fromAccountNumber.equals(toAccountNumber)) {
            return new ResponseEntity<Object>("You enter the same account number origin and destination",
                    HttpStatus.FORBIDDEN);
        }

        Client clientAuthentication = clientRepository.findByEmail(authentication.getName());
        Account accountTo = accountRepository.findByNumber(toAccountNumber);
        Account accountFrom = accountRepository.findByNumber(fromAccountNumber);

        if (accountFrom == null) {
            return new ResponseEntity<Object>("Source account does not exist",
                    HttpStatus.FORBIDDEN);
        }
        if ((accountFrom.getOwner().getId()) != clientAuthentication.getId()) {
            return new ResponseEntity<Object>("The source account does not belong to you",
                    HttpStatus.FORBIDDEN);
        }
        if (accountTo == null) {
            return new ResponseEntity<Object>("Destination account does not exist",
                    HttpStatus.FORBIDDEN);
        }
        if (accountFrom.getBalance() <= amount) {
            return new ResponseEntity<Object>("Not enough funds",
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

        return new ResponseEntity<Object>("Transaction completed", HttpStatus.CREATED);
    }
}
