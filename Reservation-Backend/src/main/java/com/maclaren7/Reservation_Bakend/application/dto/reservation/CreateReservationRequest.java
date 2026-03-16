package com.maclaren7.Reservation_Bakend.application.dto.reservation;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Request data for creating a reservation.
 *
 * @param customerName    the customer name
 * @param reservationDate the reservation date
 * @param reservationTime the reservation time
 * @param service         the service name
 */
public record CreateReservationRequest(
        @NotBlank
        @Size(max = 100)
        String customerName,

        @NotNull
        @FutureOrPresent
        LocalDate reservationDate,

        @NotNull
        LocalTime reservationTime,

        @NotBlank
        @Size(max = 100)
        String service) {
}

