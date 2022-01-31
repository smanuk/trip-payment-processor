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

        List<TripsOutput> output =  new ArrayList<>();

        // by splitting into a hash of customers you could split the processing into multiple threads
        Map<String, List<TapsInput>> mapCustomerTaps = mapCustomers(taps);
        for (List<TapsInput> customerTaps : mapCustomerTaps.values()) {

            output.addAll(processCustomerTaps(customerTaps));

        }

        return output;
    }


    // Needs refactoring
    // Am not really happy with how this looks.
    public List<TripsOutput> processCustomerTaps(List<TapsInput> customerTaps) {

        Stack<TapsInput> tapsStack = new Stack<>();
        List<TripsOutput> result = new ArrayList<>();

        for (TapsInput tap : customerTaps) {

            TapType type = tap.getTapType();
            if (type.equals(TapType.ON)) {

                // is ther one on the stack already?
                //  Check for incomplete
                if (!tapsStack.empty()) {
                    result.add(tapMapper.incomplete(tapsStack.pop()));
                }

                tapsStack.push(tap);
            }
            else {

                // cancelled
                if (tapsStack.peek().getStopId().equals(tap.getStopId())) {
                    result.add(tapMapper.cancelled(tapsStack.pop(), tap));
                    continue;
                }

                result.add(tapMapper.complete(tapsStack.pop(), tap));
            }
        }

        // There could be one left on the stack if there are no more taps
        if (!tapsStack.empty()) {
            result.add(tapMapper.incomplete(tapsStack.pop()));
        }

        return result;
    }

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
