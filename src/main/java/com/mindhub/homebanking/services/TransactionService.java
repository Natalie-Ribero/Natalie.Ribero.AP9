package com.mindhub.homebanking.services;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestParam;

public interface TransactionService {
    ResponseEntity<Object> transaction(


            @RequestParam String fromAccountNumber, @RequestParam String toAccountNumber, @RequestParam Double amount,
            @RequestParam String description, Authentication authentication);
}
