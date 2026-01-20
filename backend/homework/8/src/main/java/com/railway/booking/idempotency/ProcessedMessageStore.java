package com.railway.booking.idempotency;

import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ProcessedMessageStore{

    private final Set<UUID> processed = ConcurrentHashMap.newKeySet();

    public boolean isDuplicate(UUID eventId){

        return (!processed.add(eventId));
    }
}