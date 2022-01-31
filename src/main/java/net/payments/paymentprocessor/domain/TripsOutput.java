package net.payments.paymentprocessor.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Builder
public class TripsOutput {

    final ZonedDateTime started;
    final ZonedDateTime finished;
    final Integer duractionSecs;
    final String fromStopId;
    final String toStopId;
    final Integer chargeAmountCents;
    final String companyId;
    final String busId;
    final String pan;
    final TripStatus status;
}
