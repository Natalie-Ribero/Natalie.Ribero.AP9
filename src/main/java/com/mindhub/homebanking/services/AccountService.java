package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.AccountDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Set;

public interface AccountService {
    List<AccountDTO> getAccountDTO();
    Set<AccountDTO> getAccounts(Authentication authentication);
    ResponseEntity<Object> getAccountsById(@PathVariable Long id, Authentication authentication);
    ResponseEntity<Object> createAccount(Authentication authentication);
}
