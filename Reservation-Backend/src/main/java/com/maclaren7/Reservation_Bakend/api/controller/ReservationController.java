package com.maclaren7.Reservation_Bakend.api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maclaren7.Reservation_Bakend.application.dto.reservation.CreateReservationRequest;
import com.maclaren7.Reservation_Bakend.application.dto.reservation.ReservationResponse;
import com.maclaren7.Reservation_Bakend.application.service.ReservationService;
import com.maclaren7.Reservation_Bakend.domain.model.Reservation;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/reservas")
public class ReservationController {

    private final ReservationService reservationService;

    /**
     * Creates a new instance with the required dependencies.
     *
     * @param reservationService the service used to apply reservation business logic
     */
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    /**
     * Retrieves all reservations. 
     *
     * @return a list of reservations
     */
    @GetMapping
    public ResponseEntity<List<ReservationResponse>> listReservations() {
        List<Reservation> reservations = reservationService.listReservations();
        List<ReservationResponse> responses = reservations.stream()
                .map(this::toResponse)
                .toList();

        return ResponseEntity.ok(responses);
    }

    /**
     * Creates a new reservation.
     *
     * @param request the data required to create the reservation
     * @return the created reservation
     */
    @PostMapping
    public ResponseEntity<ReservationResponse> createReservation(
            @Valid @RequestBody CreateReservationRequest request) {

        Reservation reservationToCreate = new Reservation(
                null,
                request.customerName(),
                request.reservationDate(),
                request.reservationTime(),
                request.service(),
                null);

        Reservation createdReservation = reservationService.createReservation(reservationToCreate);

        ReservationResponse response = toResponse(createdReservation);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Cancels an existing reservation by its identifier.
     *
     * @param reservationId the identifier of the reservation to cancel
     * @return an empty response with no content
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelReservation(@PathVariable("id") Long reservationId) {
        reservationService.cancelReservation(reservationId);
        return ResponseEntity.noContent().build();
    }

    private ReservationResponse toResponse(Reservation reservation) {
        return new ReservationResponse(
                reservation.getId(),
                reservation.getCustomerName(),
                reservation.getReservationDate(),
                reservation.getReservationTime(),
                reservation.getService(),
                reservation.getStatus());
    }
}

