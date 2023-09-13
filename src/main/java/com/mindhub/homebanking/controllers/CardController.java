package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


import java.util.Random;
import java.util.Set;


@RestController
@RequestMapping("/api")
public class CardController {

    @Autowired
    CardService cardService;


    @GetMapping("/clients/current/cards")
    public Set<CardDTO> getCards(Authentication authentication) {
        return cardService.getCards(authentication);
    }

    @PostMapping("/clients/current/cards")
    public ResponseEntity<Object> createCard(@RequestParam CardType cardType, @RequestParam CardColor cardColor,
                                             Authentication authentication) {
        return cardService.createCard(cardType, cardColor, authentication);
    }
}


