package com.maclaren7.Reservation_Bakend.domain.repository;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.data.jpa.repository.JpaRepository;

import com.maclaren7.Reservation_Bakend.domain.model.Reservation;

/**
 * Repository for managing {@link Reservation} entities.
 */
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    /**
     * Checks if there is an existing reservation for the given date and time.
     *
     * @param reservationDate the reservation date to check
     * @param reservationTime the reservation time to check
     * @return {@code true} if a reservation exists for the given date and time;
     *         {@code false} otherwise
     */
    boolean existsByReservationDateAndReservationTime(LocalDate reservationDate, LocalTime reservationTime);
}

