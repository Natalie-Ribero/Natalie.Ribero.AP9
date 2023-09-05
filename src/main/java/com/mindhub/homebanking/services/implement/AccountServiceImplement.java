package com.mindhub.homebanking.services.implement;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AccountServiceImplement implements AccountService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ClientRepository clientRepository;

    @Override
    public List<AccountDTO> getAccountDTO() {
        List<Account> allAccounts = accountRepository.findAll();
        return allAccounts
                .stream()
                .map(AccountDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public Set<AccountDTO> getAccounts(Authentication authentication) {
        Client clientAuthentication = clientRepository.findByEmail(authentication.getName());
        Set<Account> accounts = clientAuthentication.getAccounts();
        return accounts
                .stream()
                .map(AccountDTO::new)
                .collect(Collectors.toSet());
    }

    @Override
    public ResponseEntity<Object> getAccountsById(Long id, Authentication authentication) {
        Client clientAuthentication = clientRepository.findByEmail(authentication.getName());
        Set<Account> accounts = clientAuthentication.getAccounts();
        Account account = accountRepository.findById(id).orElse(null);
        if (account == null){
            return new ResponseEntity<Object>("la cuenta no existe", HttpStatus.FORBIDDEN);
        }
        if ((account.getOwner().getId()) != clientAuthentication.getId()){
            return new ResponseEntity<Object>("La cuenta de origen no le pertenece", HttpStatus.FORBIDDEN);
        } else {
            AccountDTO accountDto = new AccountDTO(account);
            return new ResponseEntity <Object> (accountDto, HttpStatus.ACCEPTED);
        }
    }

    @Override
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
