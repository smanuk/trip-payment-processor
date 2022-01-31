package net.payments.paymentprocessor.service;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

/* Just a rough mocked service that give you prices for a journey between two points
    or the max price from a stop
 */
public class PriceService {

    public static String STOP1 = "Stop1";
    public static String STOP2 = "Stop2";
    public static String STOP3 = "Stop3";

    private Map<String, Map<String, Integer>> prices;

    public PriceService() {

        prices = new HashMap<>();

        Map<String, Integer> stop1 = new HashMap<>();
        stop1.put(STOP2, 325);
        stop1.put(STOP3, 730);
        prices.put(STOP1, stop1);

        Map<String, Integer> stop2 = new HashMap<>();
        stop2.put(STOP1, 325);
        stop2.put(STOP3, 550);
        prices.put(STOP2, stop2);

        Map<String, Integer> stop3 = new HashMap<>();
        stop3.put(STOP1, 730);
        stop3.put(STOP2, 550);
        prices.put(STOP3, stop3);
    }

    // PRE: both stop and start need to be supplied
    public int getPrice(String start, String stop) {

        return prices.get(start).get(stop);
    }

    public int getMax(String start) {

        return prices.get(start).values().stream()
                .mapToInt(v -> v)
                .max().orElseThrow(NoSuchElementException::new);
    }
}
