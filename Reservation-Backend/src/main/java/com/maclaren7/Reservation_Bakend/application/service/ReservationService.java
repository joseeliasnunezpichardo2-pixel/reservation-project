package com.maclaren7.Reservation_Bakend.application.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maclaren7.Reservation_Bakend.common.exception.ReservationBusinessException;
import com.maclaren7.Reservation_Bakend.domain.model.Reservation;
import com.maclaren7.Reservation_Bakend.domain.model.ReservationStatus;
import com.maclaren7.Reservation_Bakend.domain.repository.ReservationRepository;

/**
 * Service containing the business logic for reservations.
 */
@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    /**
     * Creates a new instance with the required dependencies.
     *
     * @param reservationRepository the repository used to manage reservations
     */
    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    /**
     * Retrieves all existing reservations.
     *
     * @return a list containing all reservations
     */
    @Transactional(readOnly = true)
    public List<Reservation> listReservations() {
        return reservationRepository.findAll();
    }

    /**
     * Creates a reservation if there is no other reservation at the same date and time.
     *
     * @param reservation the reservation to create
     * @return the created reservation
     * @throws ReservationBusinessException if a reservation already exists at the given date and time
     */
    @Transactional
    public Reservation createReservation(Reservation reservation) {
        boolean reservationExists = reservationRepository.existsByReservationDateAndReservationTime(
                reservation.getReservationDate(),
                reservation.getReservationTime());

        if (reservationExists) {
            throw new ReservationBusinessException(
                    "A reservation already exists for date " + reservation.getReservationDate()
                            + " and time " + reservation.getReservationTime());
        }

        if (reservation.getStatus() == null) {
            reservation.setStatus(ReservationStatus.ACTIVE);
        }

        return reservationRepository.save(reservation);
    }

    /**
     * Cancels a reservation by its identifier.
     *
     * @param reservationId the identifier of the reservation to cancel
     * @throws ReservationBusinessException if the reservation is not found or is already canceled
     */
    @Transactional
    public void cancelReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ReservationBusinessException(
                        "Reservation not found with id " + reservationId));

        if (ReservationStatus.CANCELED.equals(reservation.getStatus())) {
            throw new ReservationBusinessException("Reservation with id " + reservationId + " is already canceled");
        }

        reservation.setStatus(ReservationStatus.CANCELED);
        reservationRepository.save(reservation);
    }
}

 