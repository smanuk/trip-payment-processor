package net.payments.paymentprocessor.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
public class TripsOutput {

    ZonedDateTime started;
    ZonedDateTime finished;
    Integer duractionSecs;
    String fromStopId;
    String toStopId;
    Integer chargeAmountCents;
    String companyId;
    String busId;
    String pan;
    TripStatus status;
}
