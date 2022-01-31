package net.payments.paymentprocessor.service;

import com.opencsv.CSVWriter;
import net.payments.paymentprocessor.domain.TripsOutput;

import java.io.FileWriter;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TripsFileWriter {
    private final String[] header = {"Started", "Finished", "DurationSecs", "FromStopId",
            "ToStopId", "ChargeAmount", "CompanyId", "BusID", "PAN", "Status" };



    public TripsFileWriter() {


    }

    public void writeFile(List<TripsOutput> output, String filename) {

        try {
            CSVWriter csvWriter = new CSVWriter(new FileWriter(filename), ',', CSVWriter.NO_QUOTE_CHARACTER);
            writeHeader(csvWriter);

            for (TripsOutput trip : output) {
                csvWriter.writeNext(mapToArray(trip));
            }

            csvWriter.close();
        }
        catch (Exception ex) {

            System.out.println(ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    private void writeHeader(CSVWriter csvWriter) {

        csvWriter.writeNext(header);
    }

    private String[] mapToArray(TripsOutput trip) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(TapsFileReader.FORMAT);

        String[] valuesArray = new String[10];

        valuesArray[0] = trip.getStarted().format(formatter);
        valuesArray[1] = trip.getFinished().format(formatter);
        valuesArray[2] = trip.getDuractionSecs().toString();
        valuesArray[3] = trip.getFromStopId();
        valuesArray[4] = trip.getToStopId();
        valuesArray[5] = String.valueOf(trip.getChargeAmountCents()/100f);
        valuesArray[6] = trip.getCompanyId();
        valuesArray[7] = trip.getBusId();
        valuesArray[8] = trip.getPan();
        valuesArray[9] = trip.getStatus().toString();

        return valuesArray;
    }
}
