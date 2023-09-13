package com.mindhub.homebanking.utils;

import com.mindhub.homebanking.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Random;

public class AccountUtils {
    @Autowired
    static AccountRepository accountRepository;

    public static String createNumberAccount() {
        String formatNumberAccount;
        do {
            Random random = new Random();
            long numberAccount = random.nextInt(90000000) + 1;
            formatNumberAccount = "VIN-" + numberAccount;
            return formatNumberAccount;
        } while (accountRepository.existsByNumber(formatNumberAccount));
    }
}
