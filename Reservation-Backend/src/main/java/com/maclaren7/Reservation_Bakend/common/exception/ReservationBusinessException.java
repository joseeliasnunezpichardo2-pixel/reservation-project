package com.maclaren7.Reservation_Bakend.common.exception;

/**
 * Exception thrown when a reservation business rule is violated.
 */
public class ReservationBusinessException extends RuntimeException {

    /**
     * Creates a new instance with the provided message.
     *
     * @param message the detail message describing the business rule violation
     */
    public ReservationBusinessException(String message) {
        super(message);
    }
}

