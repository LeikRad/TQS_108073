package leikrad.dev.homework1.boundary;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.persistence.EntityNotFoundException;
import leikrad.dev.homework1.data.reservation.Reservation;
import leikrad.dev.homework1.service.ReservationManagerService;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api")
public class ReservationController {

    private final ReservationManagerService reservationManagerService;
    private static final Logger logger = LoggerFactory.getLogger(ReservationController.class);

    public ReservationController(ReservationManagerService reservationManagerService) {
        this.reservationManagerService = reservationManagerService;
    }

    @PostMapping("/reservation")
    public ResponseEntity<Reservation> createReservation(@RequestBody Reservation reservation) {
        try {
            Reservation newReservation = reservationManagerService.createReservation(reservation);
            logger.info("Created reservation: {}", newReservation);
            return new ResponseEntity<>(newReservation, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            logger.error("Error creating reservation: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/reservation")
    public ResponseEntity<List<Reservation>> getAllReservations() {
        try {
            List<Reservation> reservations = reservationManagerService.getAllReservations();
            logger.info("Retrieved all reservations: {}", reservations);
            return new ResponseEntity<>(reservations, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            logger.error("Error retrieving all reservations: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/reservation/{id}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable Long id) {
        logger.info("Retrieved reservation by id: {}", id);
        return reservationManagerService.getReservationDetails(id)
                .map(reservation -> new ResponseEntity<>(reservation, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/reservation/uuid/{uuid}")
    public ResponseEntity<Reservation> getReservationByUuid(@PathVariable String uuid) {
        logger.info("Retrieved reservation by uuid: {}", uuid);
        return reservationManagerService.getReservationByUuid(uuid)
                .map(reservation -> new ResponseEntity<>(reservation, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/reservation/{id}")
    public ResponseEntity<Reservation> updateReservation(@PathVariable Long id, @RequestBody Reservation reservation) {
        try {
            reservation.setReservationId(id);
            Reservation updatedReservation = reservationManagerService.updateReservation(reservation);
            logger.info("Updated reservation: {}", updatedReservation);
            return new ResponseEntity<>(updatedReservation, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            logger.error("Error updating reservation: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/reservation/{id}")
    public ResponseEntity<HttpStatus> deleteReservation(@PathVariable Long id) {
        try {
            reservationManagerService.deleteReservation(id);
            logger.info("Deleted reservation with id: {}", id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException e) {
            logger.error("Error deleting reservation: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
