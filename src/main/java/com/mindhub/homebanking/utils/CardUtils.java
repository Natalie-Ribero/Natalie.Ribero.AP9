package com.mindhub.homebanking.utils;

import com.mindhub.homebanking.repositories.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Random;

public final class CardUtils {
    public CardUtils() {}

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
}
