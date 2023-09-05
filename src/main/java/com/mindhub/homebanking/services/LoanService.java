package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.LoanApplicationDTO;
import com.mindhub.homebanking.dtos.LoanDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface LoanService {
    List<LoanDTO> getLoans();
    ResponseEntity<Object> applyLoans(@RequestBody LoanApplicationDTO loanApplicationDTO, Authentication authentication);
}
