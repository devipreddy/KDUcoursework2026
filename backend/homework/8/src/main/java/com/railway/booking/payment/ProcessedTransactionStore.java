package com.railway.booking.payment;

import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ProcessedTransactionStore {

    private final Set<String> processedTransactions =
            ConcurrentHashMap.newKeySet();

    public boolean alreadyProcessed(String transactionId) {
        return processedTransactions.contains(transactionId);
    }

    public void markProcessed(String transactionId) {
        processedTransactions.add(transactionId);
    }
}
