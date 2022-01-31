package net.payments.paymentprocessor.service;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import lombok.Getter;
import lombok.Setter;
import net.payments.paymentprocessor.domain.TapType;
import net.payments.paymentprocessor.domain.TapsInput;

import java.io.FileReader;
import java.io.Reader;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Setter
public class TapsFileReader {

    public static String FORMAT = "dd-MM-yyyy HH:mm:ss";

    String filename;
    private List<TapsInput> tapsInputList;

    public TapsFileReader(String filename) {

        this.filename = filename;
        tapsInputList =  new ArrayList<>();
    }

    public List<TapsInput> getTapsInputList() {

        return Collections.unmodifiableList(tapsInputList);
    }

    public TapsFileReader processFile() {

        return this;
    }

    private TapsInput mappper(String[] values) {

        return TapsInput.builder()
                .id(values[0].trim())
                .tapTime(getZdt(values[1].trim()))
                .tapType(getTapType(values[2].trim()))
                .stopId(values[3].trim())
                .companyId(values[4].trim())
                .busId(values[5].trim())
                .pan(values[6].trim())
                .build();
    }

    private TapType getTapType(String tapName) {

        return TapType.valueOf(tapName);
    }

    private ZonedDateTime getZdt(String stringDate) {

        return ZonedDateTime.parse(stringDate,
                DateTimeFormatter.ofPattern(FORMAT)
                        .withZone(ZoneOffset.UTC));
    }

    private Reader loadfile() throws Exception {

        return Files.newBufferedReader(Paths.get(
                ClassLoader.getSystemResource(filename).toURI()));
    }
}
