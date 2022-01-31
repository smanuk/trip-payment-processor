package net.payments.paymentprocessor.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
@Builder
public class TapsInput {

    String id;
    ZonedDateTime tapTime;
    TapType tapType;
    String stopId;
    String companyId;
    String busId;
    String pan;
}
