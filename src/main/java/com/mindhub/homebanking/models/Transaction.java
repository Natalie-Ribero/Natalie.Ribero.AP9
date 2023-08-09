package com.mindhub.homebanking.models;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Transaction {
private Long id;
private String description;
private LocalDate date;
private Double amount;
}
