package net.payments.paymentprocessor.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
public class TapsInput {

    String id;
    ZonedDateTime tapTime;
    TapType tapType;
    String stopId;
    String companyId;
    String busId;
    String pan;
}
