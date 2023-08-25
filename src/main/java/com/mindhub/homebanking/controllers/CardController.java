package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class CardController {

    @Autowired
    ClientRepository clientRepository;
    @Autowired
    CardRepository cardRepository;

    @RequestMapping(path = "/clients/current/cards", method = RequestMethod.POST)
    public ResponseEntity<Object> createCard(@RequestParam CardType cardType, @RequestParam CardColor cardColor,
                                           Authentication authentication) {

        Client clientAuthentication = clientRepository.findByEmail(authentication.getName());
        Set<Card> cards = clientAuthentication.getCards();
        Set<Card> debitCards = cards.stream()
                .filter(card -> card.getType() == CardType.DEBIT)
                .collect(Collectors.toSet());
        Set<Card> creditCards = cards.stream()
                .filter(card -> card.getType() == CardType.CREDIT)
                .collect(Collectors.toSet());

        switch (cardType) {
            case CREDIT:
                if (creditCards.size() < 3) {
                    //todo:Crear una funcion  que cree la tarjeta y tome como parametro el tipo para no repetir lo mismo.
                    //todo: solo pueden haber 3 tarjetas de cada tipo y una de cada color.
                    Card card = new Card(clientAuthentication.toString(), CardType.CREDIT, cardColor,
                            Card.createNumberCard(),
                            Card.createCvv(), LocalDate.now(), LocalDate.now().plusYears(5));
                    cardRepository.save(card);
                    clientAuthentication.addCard(card);
                    clientRepository.save(clientAuthentication);
                    return new ResponseEntity<Object>("Tarjeta asignada",HttpStatus.CREATED);
                } else {
                    return new ResponseEntity<Object>("Ya tiene el maximo permitido de tarjetas de credito",HttpStatus.FORBIDDEN);
                }
            case DEBIT:
                if (debitCards.size() < 3) {
                    Card card = new Card(clientAuthentication.toString(), CardType.DEBIT, cardColor,
                            Card.createNumberCard(),
                            Card.createCvv(), LocalDate.now(), LocalDate.now().plusYears(5));
                    cardRepository.save(card);
                    clientAuthentication.addCard(card);
                    clientRepository.save(clientAuthentication);
                    return new ResponseEntity<Object>("Tarjeta asignada",HttpStatus.CREATED);
                } else {
                    return new ResponseEntity<Object>("Ya tiene el maximo permitido de tarjetas de debito",HttpStatus.FORBIDDEN);
                }
            default:
                return new ResponseEntity<Object>("La opcion elegida no es valida",HttpStatus.FORBIDDEN);
        }
    }
}
