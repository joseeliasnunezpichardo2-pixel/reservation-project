package com.maclaren7.Reservation_Bakend.application.dto.reservation;

import java.time.LocalDate;
import java.time.LocalTime;

import com.maclaren7.Reservation_Bakend.domain.model.ReservationStatus;

/**
 * Response data for a reservation.
 *
 * @param id              the reservation identifier
 * @param customerName    the customer name
 * @param reservationDate the reservation date
 * @param reservationTime the reservation time
 * @param service         the service name
 * @param status          the reservation status
 */
public record ReservationResponse(
        Long id,
        String customerName,
        LocalDate reservationDate,
        LocalTime reservationTime,
        String service,
        ReservationStatus status) {
}

