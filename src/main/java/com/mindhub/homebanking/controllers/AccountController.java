package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class AccountController {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ClientRepository clientRepository;


    @GetMapping("/accounts")
    public List<AccountDTO> getAccount() {
        List<Account> allAccounts = accountRepository.findAll();
        return allAccounts
                .stream()
                .map(AccountDTO::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/accounts/{id}")
    public AccountDTO getAccountsById(@PathVariable Long id, Authentication authentication) {
        Client clientAuthentication = clientRepository.findByEmail(authentication.getName());
        Set<Account> accounts = clientAuthentication.getAccounts();

        Account account = accountRepository.findById(id).orElse(null);
        return new AccountDTO(account.get());
    }

    @GetMapping("/clients/current/accounts")
    public Set<AccountDTO> getAccount(Authentication authentication) {
        Client clientAuthentication = clientRepository.findByEmail(authentication.getName());
        Set<Account> accounts = clientAuthentication.getAccounts();
        return accounts
                .stream()
                .map(AccountDTO::new)
                .collect(Collectors.toSet());
    }

    @RequestMapping(path = "/clients/current/accounts", method = RequestMethod.POST)
    public ResponseEntity<Object> createAccount(Authentication authentication) {
        Client clientAuthentication = clientRepository.findByEmail(authentication.getName());
        if (clientAuthentication.getAccounts().size() < 3) {
            Account account = new Account(Account.createNumberAccount(), LocalDate.now(), 0.00);
            accountRepository.save(account);
            clientAuthentication.addAccount(account);
            clientRepository.save(clientAuthentication);
            return new ResponseEntity<Object>("Su cuenta a sido creada", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<Object>("Hubo un error en la creacion de su cuenta", HttpStatus.FORBIDDEN);
        }
    }
}
