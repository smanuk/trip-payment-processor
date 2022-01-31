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
    private Map<String, List<TapsInput>> mapCustomers(List<TapsInput> taps) {

        Map<String, List<TapsInput>> customerMap = new HashMap<>();

        for (TapsInput tap : taps) {

            String pan = tap.getPan();
            if (customerMap.containsKey(pan)) {

                customerMap.get(pan).add(tap);
            }
            else {
                List<TapsInput> newCustomer = new ArrayList<>();
                newCustomer.add(tap);
                customerMap.put(pan, newCustomer);
            }
        }

        return customerMap;
    }
}
