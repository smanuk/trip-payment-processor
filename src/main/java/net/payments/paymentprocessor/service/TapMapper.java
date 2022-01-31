package net.payments.paymentprocessor.service;

import net.payments.paymentprocessor.domain.TapsInput;
import net.payments.paymentprocessor.domain.TripStatus;
import net.payments.paymentprocessor.domain.TripsOutput;

import java.time.temporal.ChronoUnit;
import java.util.Optional;

public class TapMapper {

    //@Inject
    private PriceService priceService;

    public TapMapper() {
        priceService =  new PriceService();
    }

    public TripsOutput complete(TapsInput start, TapsInput stop) {

        return buildOutput(start, Optional.of(stop), TripStatus.COMPLETED);
    }

    public TripsOutput incomplete(TapsInput start) {

        return buildOutput(start, Optional.empty(), TripStatus.INCOMPLETE);
    }

    public TripsOutput cancelled(TapsInput start, TapsInput stop) {

        return buildOutput(start, Optional.of(stop), TripStatus.CANCELLED);
    }


    private TripsOutput buildOutput(TapsInput start, Optional<TapsInput> stop, TripStatus status) {

        TripsOutput.TripsOutputBuilder builder = TripsOutput.builder()
                .started(start.getTapTime())
                .fromStopId(start.getStopId())
                .toStopId(start.getStopId())
                .companyId(start.getCompanyId())
                .busId(start.getBusId())
                .pan(start.getPan())
                .status(status);

                if (stop.isPresent()){

                    builder.finished(stop.get().getTapTime());
                    builder.duractionSecs((int)ChronoUnit.SECONDS.between(stop.get().getTapTime(), stop.get().getTapTime()));
                    builder.chargeAmountCents(priceService.getPrice(start.getStopId(), stop.get().getStopId()));
                }
                else {
                    if (status == TripStatus.INCOMPLETE) {
                        builder.chargeAmountCents(priceService.getMax(start.getStopId()));
                    }
                }

        return builder.build();
    }

}
