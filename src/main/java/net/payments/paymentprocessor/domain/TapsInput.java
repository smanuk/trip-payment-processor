package net.payments.paymentprocessor.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Builder
public class TapsInput {

    final String id;
    final ZonedDateTime tapTime;
    final TapType tapType;
    final String stopId;
    final String companyId;
    final String busId;
    final String pan;
}
