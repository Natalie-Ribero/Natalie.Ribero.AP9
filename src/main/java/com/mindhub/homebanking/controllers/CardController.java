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
    public ResponseEntity<Object> createCard(@RequestParam CardType type, @RequestParam CardColor color,
                                           Authentication authentication) {

        Client clientAuthentication = clientRepository.findByEmail(authentication.getName());
        Set<Card> cards = clientAuthentication.getCards();
        Set<Card> debitCards = cards.stream()
                .filter(card -> card.getType() == CardType.DEBIT)
                .collect(Collectors.toSet());
        Set<Card> creditCards = cards.stream()
                .filter(card -> card.getType() == CardType.CREDIT)
                .collect(Collectors.toSet());

        switch (type) {
            case CREDIT:
                if (creditCards.size() < 3) {
                    Card card = new Card(clientAuthentication.toString(), CardType.CREDIT, color,
                            Card.createNumberCard(),
                            Card.createCvv(), LocalDate.now(), LocalDate.now().plusYears(5));
                    cardRepository.save(card);
                    clientAuthentication.addCard(card);
                    clientRepository.save(clientAuthentication);
                    return new ResponseEntity<>(HttpStatus.CREATED);
                } else {
                    return new ResponseEntity<>(HttpStatus.FORBIDDEN);
                }
            case DEBIT:
                if (debitCards.size() < 3) {
                    Card card = new Card(clientAuthentication.toString(), CardType.DEBIT, color,
                            Card.createNumberCard(),
                            Card.createCvv(), LocalDate.now(), LocalDate.now().plusYears(5));
                    cardRepository.save(card);
                    clientAuthentication.addCard(card);
                    clientRepository.save(clientAuthentication);
                    return new ResponseEntity<>(HttpStatus.CREATED);
                } else {
                    return new ResponseEntity<>(HttpStatus.FORBIDDEN);
                }
            default:
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
}
