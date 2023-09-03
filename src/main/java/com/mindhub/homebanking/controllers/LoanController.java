package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.ClientLoan;
import com.mindhub.homebanking.models.Loan;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class LoanController {

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private ClientLoanRepository clientLoanRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private AccountRepository accountRepository;

    @GetMapping("/loans")
    public List<LoanDTO> getLoans() {
        return loanRepository.findAll()
                .stream()
                .map(LoanDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    @RequestMapping(path = "/loans", method = RequestMethod.POST)
    public ResponseEntity<Object> applyLoans(@RequestBody Loan loan, Authentication authentication) {

        Client clientAuthentication = clientRepository.findByEmail(authentication.getName());

        if () {
            return new ResponseEntity<Object>("Verifique los datos",
                    HttpStatus.FORBIDDEN);
        }
        if () {
            return new ResponseEntity<Object>("La cuenta de destino no existe",
                    HttpStatus.FORBIDDEN);
        }
        if () {
            return new ResponseEntity<Object>("La cuenta destino no le pertenece",
                    HttpStatus.FORBIDDEN);
        }
        if () {
            return new ResponseEntity<Object>("el prestamo solicitado no existe",
                    HttpStatus.FORBIDDEN);
        }
        if () {
            return new ResponseEntity<Object>("El monto solicitado es mayor al permitido para este tipo de prestamos",
                    HttpStatus.FORBIDDEN);
        }
        if () {
            return new ResponseEntity<Object>("Las cuotas seleccionadas no estan disponibles para este tipo de " +
                    "prestamos",
                    HttpStatus.FORBIDDEN);
        }
        //todo: solicitud de préstamo creado al cliente autenticado
        ClientLoan clientLoan = new ClientLoan();
        Transaction transaction = new Transaction();
        account.addTransaction(transaction);
        client.addLoan(clientLoan);
        loan.addClient(clientLoan);
        clientLoanRepository.save(clientLoan);
        transactionRepository.save(transaction);
        //todo:transacción creada para cuenta de destino
        //todo:cuenta de destino actualizada con el monto

    }
}
