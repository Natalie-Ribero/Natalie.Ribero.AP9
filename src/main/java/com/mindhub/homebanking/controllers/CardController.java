package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class CardController {

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private CardRepository cardRepository;

    @GetMapping("/clients/current/cards")
    public Set<CardDTO> getCards(Authentication authentication) {
        Client clientAuthentication = clientRepository.findByEmail(authentication.getName());
        Set<Card> cards = clientAuthentication.getCards();
        return cards
                .stream()
                .map(CardDTO::new)
                .collect(Collectors.toSet());
    }

    public static String createCvv() {
        Random random = new Random();
        int cvv = random.nextInt(900) + 1;
        return String.format("%03d", cvv);
    }

    @Autowired
    private static CardRepository cardRepository1;

    public static String createNumberCard() {
        StringBuilder createString = new StringBuilder();
        String numberFinalCard;
        do {
            Random random = new Random();
            for (int i = 0; i < 4; i++) {
                int numberCard = random.nextInt(9000) + 1;
                createString.append(String.format("%04d", numberCard));
                if (i < 3) {
                    createString.append("-");
                }
            }
            numberFinalCard = createString.toString();
            return numberFinalCard;
        } while (cardRepository1.existsByNumber(numberFinalCard));
    }

    @RequestMapping(path = "/clients/current/cards", method = RequestMethod.POST)
    public ResponseEntity<Object> createCard(@RequestParam CardType cardType, @RequestParam CardColor cardColor,
                                             Authentication authentication) {

        Client clientAuthentication = clientRepository.findByEmail(authentication.getName());
        Set<Card> cards = clientAuthentication.getCards();
        if (cards.stream().filter(card -> card.getType().equals(cardType)).filter(card -> card.getColor().equals(cardColor)).collect(Collectors.toSet()).isEmpty()) {
            Card card = new Card(clientAuthentication.toString(), cardType, cardColor, createNumberCard(), createCvv(), LocalDate.now(), LocalDate.now().plusYears(5));
            cardRepository.save(card);
            clientAuthentication.addCard(card);
            clientRepository.save(clientAuthentication);
            return new ResponseEntity<Object>("Tarjeta asignada", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<Object>("Usted ya tiene una tarjeta de este tipo", HttpStatus.FORBIDDEN);
        }
    }
}


