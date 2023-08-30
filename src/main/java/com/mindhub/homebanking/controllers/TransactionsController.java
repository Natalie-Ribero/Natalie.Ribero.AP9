package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api")
public class TransactionsController {

    @RequestMapping(path = "/transactions", method = RequestMethod.POST)

    public ResponseEntity<Object> register(

            @RequestParam Double amount, @RequestParam String description,
            @RequestParam String accountDestination, @RequestParam String accountSource) {

        if (amount.isNaN() || description.isEmpty() || accountDestination.isEmpty() || accountSource.isEmpty()) {
            return new ResponseEntity<>("Verifique de nuevo los datos", HttpStatus.FORBIDDEN);
        }
        if (accountSource.equals(accountDestination)){
            return new ResponseEntity<>("Usted ingreso el mismo numero de cuenta origen y destino", HttpStatus.FORBIDDEN);
        }
        if (){}


        return new ResponseEntity<Object>("Transaccion realizada",HttpStatus.CREATED);


    }

}
