package net.payments.paymentprocessor.service;

import net.payments.paymentprocessor.domain.TapType;
import net.payments.paymentprocessor.domain.TapsInput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TapsFileReaderTest {

    private final String test_file1 = "test_complete1.csv";
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

    @Test
    void tapsFileReaderTest_imported_data_correct_first() {

        List<TapsInput> actual = cut.processFile().getTapsInputList();

        TapsInput input = actual.get(0);
        assertAll("Row 1 data points",
                () -> assertEquals("1", input.getId()),
                () -> assertEquals(getZdt("22-01-2018 13:00:00"), input.getTapTime()),
                () -> assertEquals(TapType.ON, input.getTapType()),
                () -> assertEquals("Stop1", input.getStopId()),
                () -> assertEquals("Company1", input.getCompanyId()),
                () -> assertEquals("Bus37", input.getBusId()),
                () -> assertEquals("5500005555555559", input.getPan())
        );
    }


    @Test
    void tapsFileReaderTest_imported_data_correct_second() {

        List<TapsInput> actual = cut.processFile().getTapsInputList();

        TapsInput input = actual.get(1);
        assertAll("Row 2 data points",
                () -> assertEquals("2", input.getId()),
                () -> assertEquals(getZdt("22-01-2018 13:05:00"), input.getTapTime()),
                () -> assertEquals(TapType.OFF, input.getTapType()),
                () -> assertEquals("Stop2", input.getStopId()),
                () -> assertEquals("Company1", input.getCompanyId()),
                () -> assertEquals("Bus37", input.getBusId()),
                () -> assertEquals("5500005555555559", input.getPan())
        );
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
