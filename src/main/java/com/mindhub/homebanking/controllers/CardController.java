package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.CardService;
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
    CardService cardService;


    @GetMapping("/clients/current/cards")
    public Set<CardDTO> getCards(Authentication authentication) {
        return cardService.getCards(authentication);
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
        return cardService.createCard(cardType, cardColor, authentication);
    }
}


