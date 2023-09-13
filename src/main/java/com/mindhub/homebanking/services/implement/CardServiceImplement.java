package com.mindhub.homebanking.services.implement;

import com.mindhub.homebanking.controllers.CardController;
import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.CardService;
import com.mindhub.homebanking.utils.CardUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CardServiceImplement implements CardService {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private CardRepository cardRepository;

    @Override
    public Set<CardDTO> getCards(Authentication authentication) {
        Client clientAuthentication = clientRepository.findByEmail(authentication.getName());
        Set<Card> cards = clientAuthentication.getCards();
        return cards
                .stream()
                .map(CardDTO::new)
                .collect(Collectors.toSet());
    }

    @Override
    public ResponseEntity<Object> createCard(CardType cardType, CardColor cardColor, Authentication authentication) {
        Client clientAuthentication = clientRepository.findByEmail(authentication.getName());
        Set<Card> cards = clientAuthentication.getCards();
        if (cards.stream().filter(card -> card.getType().equals(cardType)).filter(card -> card.getColor().equals(cardColor)).collect(Collectors.toSet()).isEmpty()) {
            Card card = new Card(clientAuthentication.toString(), cardType, cardColor, CardUtils.createNumberCard(),
                    CardUtils.createCvv(), LocalDate.now(), LocalDate.now().plusYears(5));
            cardRepository.save(card);
            clientAuthentication.addCard(card);
            clientRepository.save(clientAuthentication);
            return new ResponseEntity<Object>("Card assigned", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<Object>("You already have such a card", HttpStatus.FORBIDDEN);
        }
    }

}
