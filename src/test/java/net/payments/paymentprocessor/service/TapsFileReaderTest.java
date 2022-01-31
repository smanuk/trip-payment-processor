package net.payments.paymentprocessor.service;

import net.payments.paymentprocessor.domain.TapType;
import net.payments.paymentprocessor.domain.TapsInput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TapsFileReaderTest {

    private final String test_file1 = "test_file1.csv";
    private TapsFileReader cut;
    private TapsInput tapsInput;

    private final String  format = "dd-MM-yyyy HH:mm:ss";
    private final String tapTime = "28-01-2022 10:00:00";
    private final String stopId = "Stop1";
    private final String companyId = "Company1";
    private final String busId = "Bus37";
    private final String pan = "5500005555555559";

    @BeforeEach
    void beforeEach() {

        setupData();
        cut = new TapsFileReader(test_file1);
    }

    @Test
    void tapsFileReaderTest_immutableList() {

        List<TapsInput> actual = cut.processFile().getTapsInputList();

        assertThrows(UnsupportedOperationException.class, () -> {
            actual.add(tapsInput);
        });
    }

    @Test
    void tapsFileReaderTest_immutableList_contains_two() {

        List<TapsInput> actual = cut.processFile().getTapsInputList();
        assertEquals(2, actual.size());
    }

    
    private void setupData() {

        tapsInput = TapsInput.builder()
                .id("1")
                .tapTime(getZdt(tapTime))
                .tapType(TapType.ON)
                .stopId(stopId)
                .companyId(companyId)
                .busId(busId)
                .pan(pan)
                .build();
    }

    private ZonedDateTime getZdt(String stringDate) {

        return ZonedDateTime.parse(stringDate,
                DateTimeFormatter.ofPattern(format)
                        .withZone(ZoneOffset.UTC));
    }
}
