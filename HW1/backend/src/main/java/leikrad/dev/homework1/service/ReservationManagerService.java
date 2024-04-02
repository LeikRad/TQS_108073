package leikrad.dev.homework1.service;

import java.util.List;
import java.util.Optional;

import leikrad.dev.homework1.data.reservation.*;

public class ReservationManagerService {
    
    private ReservationRepository reservationRepository;

    public ReservationManagerService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public Optional<Reservation> getReservationDetails(Long reservationId) {
        return reservationRepository.findByReservationId(reservationId);
    }

    public void deleteReservation(Long reservationId) {
        Optional<Reservation> reservation = reservationRepository.findByReservationId(reservationId);
        if (reservation.isPresent()) {
            reservationRepository.deleteByReservationId(reservationId);
        }
    }

    public Reservation createReservation(Reservation reservation) {
        if (reservation.getReservationId() != null) {
            reservation.setReservationId(null);
        }
        return reservationRepository.save(reservation);
    }

    public Reservation updateReservation(Reservation reservation) {
        if (reservation.getReservationId() == null) {
            return null;
        }
        return reservationRepository.save(reservation);
    }
}
