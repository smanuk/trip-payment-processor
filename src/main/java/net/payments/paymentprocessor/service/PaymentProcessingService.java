package net.payments.paymentprocessor.service;

import net.payments.paymentprocessor.domain.TapType;
import net.payments.paymentprocessor.domain.TapsInput;
import net.payments.paymentprocessor.domain.TripsOutput;

import java.util.*;

public class PaymentProcessingService {


//    @Inject
    TapMapper tapMapper;

    public PaymentProcessingService() {
        tapMapper =  new TapMapper();
    }
    
    public List<TripsOutput> processTrips(List<TapsInput> taps) {

        return Collections.EMPTY_LIST;
    }
}
