package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/accounts")
    public List<AccountDTO> getAccount() {
        return accountService.getAccountDTO();
    }

    @GetMapping("/accounts/{id}")
    public ResponseEntity<Object> getAccountsById(@PathVariable Long id, Authentication authentication) {
       return accountService.getAccountsById(id, authentication);
    }

    @GetMapping("/clients/current/accounts")
    public Set<AccountDTO> getAccounts(Authentication authentication) {
        return accountService.getAccounts(authentication);
    }

    @PostMapping("/clients/current/accounts")
    public ResponseEntity<Object> createAccount(Authentication authentication) {
        return accountService.createAccount(authentication);
    }
}
