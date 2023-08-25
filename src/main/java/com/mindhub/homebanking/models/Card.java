package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Random;

@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private Client cardOwner;

    private String cardHolder;
    private CardType type;
    private CardColor color;
    private String number;
    private String cvv;
    private LocalDate fromDate;
    private LocalDate thruDate;

    public Card() {
    }

    public Card(String cardHolder, CardType type, CardColor color, String number, String cvv, LocalDate fromDate,
                LocalDate thruDate) {
        this.cardHolder = cardHolder;
        this.type = type;
        this.color = color;
        this.number = number;
        this.cvv = cvv;
        this.fromDate = fromDate;
        this.thruDate = thruDate;
    }

    public Long getId() {
        return id;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public CardType getType() {
        return type;
    }

    public CardColor getColor() {
        return color;
    }

    public String getNumber() {
        return number;
    }

    public String getCvv() {
        return cvv;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public LocalDate getThruDate() {
        return thruDate;
    }

    public Client getCardOwner() {
        return cardOwner;
    }

    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }

    public void setType(CardType type) {
        this.type = type;
    }

    public void setColor(CardColor color) {
        this.color = color;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public void setThruDate(LocalDate thruDate) {
        this.thruDate = thruDate;
    }

    public void setCardOwner(Client cardOwner) {
        this.cardOwner = cardOwner;
    }

    public static String createCvv() {
        Random random = new Random();
        int cvv = random.nextInt(900) + 1;
        return String.format("%03d", cvv);
    }

    public static String createNumberCard() {
        //todo: verificar que el numero de tarjeta no este repetido
        Random random = new Random();
        StringBuilder createString = new StringBuilder();

        for (int i = 0; i < 4; i++) {
            int numberCard = random.nextInt(9000) + 1;
            createString.append(String.format("%04d", numberCard));
            if (i < 3) {
                createString.append("-");
            }
        }
        return createString.toString();
    }

}
