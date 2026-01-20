package com.railway.booking.service;

import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
public class SeatService {

    private static final int COACH_SIZE = 72;    // seats per coach
    private static final int MAX_COACHES = 10;   // total coaches
    private static final int TOTAL_SEATS = COACH_SIZE * MAX_COACHES;

    private final AtomicInteger seatCounter = new AtomicInteger(0);

    public String getNextSeat() {

        int seatNumber = seatCounter.getAndIncrement();

        if (seatNumber >= TOTAL_SEATS) {
            throw new IllegalStateException("No seats available");
        }

        int coachNumber = (seatNumber / COACH_SIZE) + 1;
        int seatInCoach = (seatNumber % COACH_SIZE) + 1;

        return formatSeat(coachNumber, seatInCoach);
    }

    private String formatSeat(int coachNumber, int seatInCoach) {
        return "S" + coachNumber + "-" + seatInCoach;
    }
}
