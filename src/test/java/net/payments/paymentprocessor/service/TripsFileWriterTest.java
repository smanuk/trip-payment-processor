package net.payments.paymentprocessor.service;

import net.payments.paymentprocessor.domain.TapsInput;
import net.payments.paymentprocessor.domain.TripsOutput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

public class TripsFileWriterTest {

    static private final String TEST_COMPLETE_1_CSV = "test_complete1.csv";
    static private List<TapsInput> inputComplete1;

    private PaymentProcessingService processor;

    private TripsFileWriter cut;

    @BeforeEach
    void beforeEach() {

        setupData();
        processor = new PaymentProcessingService();
        cut = new TripsFileWriter();
    }


    @Test
    void writeFile_read_write() {

        String filename = "/tmp/trips-complete.csv";
        File outputCsv = new File(filename);
        outputCsv.delete();

        List<TripsOutput> outputList = processor.processTrips(inputComplete1);
        cut.writeFile(outputList, filename);
    }


    //many more tests that can go here but I have run out of time.


    private static void setupData() {

        inputComplete1 = loadCSV(TEST_COMPLETE_1_CSV);
    }

    private static List<TapsInput> loadCSV(String file) {

        return new TapsFileReader(file).
                processFile().
                getTapsInputList();
    }
}
