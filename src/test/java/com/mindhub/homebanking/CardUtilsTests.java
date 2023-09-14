package com.mindhub.homebanking;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import com.mindhub.homebanking.utils.CardUtils;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
public class CardUtilsTests {

    @Test
    public void cardNumberIsCreated(){
        String cardNumber = CardUtils.createNumberCard();
        assertThat(cardNumber,is(not(emptyOrNullString())));
    }

    @Test
    public void cvvIsCreated(){
        String cardNumber = CardUtils.createCvv();
        assertThat(cardNumber,is(not(emptyOrNullString())));
    }

}
