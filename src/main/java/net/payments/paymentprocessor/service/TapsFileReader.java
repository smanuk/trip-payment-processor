package net.payments.paymentprocessor.service;

import lombok.Getter;
import lombok.Setter;
import net.payments.paymentprocessor.domain.TapsInput;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Setter
public class TapsFileReader {

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
    private Reader loadfile() throws Exception {

        return Files.newBufferedReader(Paths.get(
                ClassLoader.getSystemResource(filename).toURI()));
    }
}
