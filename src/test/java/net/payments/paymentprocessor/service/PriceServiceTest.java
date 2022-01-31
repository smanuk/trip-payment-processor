package net.payments.paymentprocessor.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PriceServiceTest {

    PriceService cut;


    @BeforeEach
    void beforeEach() {

        cut = new PriceService();
    }

    @Test
    void getPrice_stop1_stop2() {

        int price = cut.getPrice(PriceService.STOP1, PriceService.STOP2);
        assertEquals(325, price);
    }

    @Test
    void getPrice_stop2_stop3() {

        int price = cut.getPrice(PriceService.STOP2, PriceService.STOP3);
        assertEquals(550, price);
    }

    @Test
    void getPrice_stop1_stop3() {

        int price = cut.getPrice(PriceService.STOP1, PriceService.STOP3);
        assertEquals(730, price);
    }

    @Test
    void getPrice_stop3_stop2() {

        int price = cut.getPrice(PriceService.STOP3, PriceService.STOP2);
        assertEquals(550, price);
    }

    @Test
    void getPrice_stop3_stop1() {

        int price = cut.getPrice(PriceService.STOP3, PriceService.STOP1);
        assertEquals(730, price);
    }

    @Test
    void getPrice_stop2_stop1() {

        int price = cut.getPrice(PriceService.STOP2, PriceService.STOP1);
        assertEquals(325, price);
    }

    @Test
    void getPrice_max_from_1() {

        int price = cut.getMax(PriceService.STOP1);
        assertEquals(730, price);
    }

    @Test
    void getPrice_max_from_2() {

        int price = cut.getMax(PriceService.STOP2);
        assertEquals(550, price);
    }

    @Test
    void getPrice_max_from_3() {

        int price = cut.getMax(PriceService.STOP3);
        assertEquals(730, price);
    }
}
