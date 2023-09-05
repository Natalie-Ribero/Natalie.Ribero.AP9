package com.mindhub.homebanking.services.implement;

import com.mindhub.homebanking.dtos.LoanApplicationDTO;
import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import com.mindhub.homebanking.services.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class LoanServiceImplement implements LoanService {
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

    @Override
    public List<LoanDTO> getLoans() {
        return loanRepository.findAll()
                .stream()
                .map(LoanDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<Object> applyLoans(LoanApplicationDTO loanApplicationDTO, Authentication authentication) {
        if (loanApplicationDTO.getLoanId() == null || loanApplicationDTO.getAmount() <= 0 || loanApplicationDTO.getPayments() <= 0 || loanApplicationDTO.getToAccountNumber().isEmpty()) {
            return new ResponseEntity<Object>("Verifique los datos",
                    HttpStatus.FORBIDDEN);
        }

        if (!accountRepository.existsByNumber(loanApplicationDTO.getToAccountNumber())) {
            return new ResponseEntity<Object>("La cuenta de destino no existe",
                    HttpStatus.FORBIDDEN);
        }

        Client clientAuthentication = clientRepository.findByEmail(authentication.getName());
        Account account = accountRepository.findByNumber(loanApplicationDTO.getToAccountNumber());

        if (!Objects.equals(account.getOwner().getEmail(), clientAuthentication.getEmail())) {
            return new ResponseEntity<Object>("La cuenta destino no le pertenece",
                    HttpStatus.FORBIDDEN);
        }

        Loan loan = loanRepository.findById(loanApplicationDTO.getLoanId()).orElse(null);

        if (loan == null) {
            return new ResponseEntity<Object>("el prestamo solicitado no existe",
                    HttpStatus.FORBIDDEN);
        }
        if (loanApplicationDTO.getAmount() > loan.getMaxAmount()) {
            return new ResponseEntity<Object>("El monto solicitado es mayor al permitido para este tipo de prestamos",
                    HttpStatus.FORBIDDEN);
        }
        if (!(loan.getPayments().contains(loanApplicationDTO.getPayments()))) {
            return new ResponseEntity<Object>("Las cuotas seleccionadas no estan disponibles para este tipo de " +
                    "prestamos",
                    HttpStatus.FORBIDDEN);
        }

        ClientLoan clientLoan = new ClientLoan(loanApplicationDTO.getPayments(),
                (loanApplicationDTO.getAmount() * 0.20));
        clientLoan.addLoanAndClient(loan, clientAuthentication);
        Transaction transaction = new Transaction(TransactionType.CREDIT, loanApplicationDTO.getAmount(),
                loan.getName() + " loan approved",
                LocalDate.now());
        transaction.addAccount(account);
        account.addTransaction(transaction);
        account.setBalance(account.getBalance() + loanApplicationDTO.getAmount());
        clientLoanRepository.save(clientLoan);
        transactionRepository.save(transaction);
        accountRepository.save(account);
        return new ResponseEntity<Object>("Prestamo solicitado corectamente", HttpStatus.ACCEPTED);
    }
}

