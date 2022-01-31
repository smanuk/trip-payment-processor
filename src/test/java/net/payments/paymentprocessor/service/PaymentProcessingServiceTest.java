package net.payments.paymentprocessor.service;

import net.payments.paymentprocessor.domain.TapsInput;
import net.payments.paymentprocessor.domain.TripStatus;
import net.payments.paymentprocessor.domain.TripsOutput;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class PaymentProcessingServiceTest {

    //complete files
    static private final String TEST_COMPLETE_1_CSV = "test_complete1.csv";
    static private List<TapsInput> inputComplete1;

    //incompleteFiles
    static private final String TEST_INCOMPLETE_1_CSV = "test_incomplete1.csv";
    static private List<TapsInput> inputIncomplete1;


    //CancelledFiles
    static private final String TEST_CANCELLED_1_CSV = "test_cancelled1.csv";
    static private List<TapsInput> inputCanelled1;

    PaymentProcessingService cut;

    @BeforeAll
    static void beforeAll() {

        setupData();
    }

    @BeforeEach
    void beforeEach() {

        cut = new PaymentProcessingService();
    }


    @Test
    void processTrips_one_complete_trip() {

        List<TripsOutput> actual = cut.processTrips(inputComplete1);

        assertEquals(1, actual.size());
        assertEquals(TripStatus.COMPLETED,actual.get(0).getStatus());
    }

    @Test
    void processTrips_one_incomplete_trip() {

        List<TripsOutput> actual = cut.processTrips(inputIncomplete1);
        assertEquals(1, actual.size());
        assertEquals(TripStatus.INCOMPLETE,actual.get(0).getStatus());
    }

    @Test
    void processTrips_one_cancelled_trip() {

        List<TripsOutput> actual = cut.processTrips(inputCanelled1);
        assertEquals(1, actual.size());
        assertEquals(TripStatus.CANCELLED,actual.get(0).getStatus());
    }

    @Test
    void processTrips_cancelled_trip_price() {

        List<TripsOutput> actual = cut.processTrips(inputCanelled1);
        assertEquals(0, actual.get(0).getChargeAmountCents());
    }

    @Test
    void processTrips_complete_trip_price() {

        List<TripsOutput> actual = cut.processTrips(inputComplete1);

        // if we had a price service we could use this to look up the expected price
        assertEquals(325, actual.get(0).getChargeAmountCents());
    }

    @Test
    void processTrips_incomplete_trip_price() {

        List<TripsOutput> actual = cut.processTrips(inputIncomplete1);

        // if we had a price service we could use this to look up the expected price
        assertEquals(550, actual.get(0).getChargeAmountCents());
    }

    @Test
    void processTrips_cancelled_duration() {

        List<TripsOutput> actual = cut.processTrips(inputCanelled1);
        assertEquals(60, actual.get(0).getDuractionSecs());
    }

    @Test
    void processTrips_complete_duration() {

        List<TripsOutput> actual = cut.processTrips(inputComplete1);
        assertEquals(60*5, actual.get(0).getDuractionSecs());
    }

    @Test
    void processTrips_incomplete_duration() {

        List<TripsOutput> actual = cut.processTrips(inputIncomplete1);
        assertEquals(0, actual.get(0).getDuractionSecs());
    }

    private static void setupData() {

        inputComplete1 = loadCSV(TEST_COMPLETE_1_CSV);

        inputIncomplete1 = loadCSV(TEST_INCOMPLETE_1_CSV);

        inputCanelled1 = loadCSV(TEST_CANCELLED_1_CSV);
    }

    private static List<TapsInput> loadCSV(String file) {

        return new TapsFileReader(file).
                processFile().
                getTapsInputList();
    }
}
