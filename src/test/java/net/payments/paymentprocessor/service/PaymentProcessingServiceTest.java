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
    void PaymentProcessingServiceTest_one_complete_trip() {

        List<TripsOutput> actual = cut.processTrips(inputComplete1);

        assertEquals(1, actual.size());
        assertEquals(TripStatus.COMPLETED,actual.get(0).getStatus());
    }

    @Test
    void PaymentProcessingServiceTest_incomplete_price_max() {

        List<TripsOutput> actual = cut.processTrips(inputIncomplete1);
        assertEquals(1, actual.size());
        assertEquals(TripStatus.INCOMPLETE,actual.get(0).getStatus());
    }

    @Test
    void PaymentProcessingServiceTest_one_cancelled_price() {

        List<TripsOutput> actual = cut.processTrips(inputCanelled1);
        assertEquals(1, actual.size());
        assertEquals(TripStatus.CANCELLED,actual.get(0).getStatus());
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
