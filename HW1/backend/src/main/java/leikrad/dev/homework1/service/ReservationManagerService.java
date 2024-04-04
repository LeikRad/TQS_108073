package leikrad.dev.homework1.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import leikrad.dev.homework1.data.reservation.*;

@Service
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
        reservationRepository.deleteByReservationId(reservationId);
    }

    public Optional<Reservation> getReservationByUuid(String uuid) {
        return reservationRepository.findByUuid(uuid);
    }

    public Reservation createReservation(Reservation reservation) {
        if (reservation.getReservationId() != null) {
            throw new IllegalArgumentException("Reservation ID must be null");
        }
        
        return reservationRepository.save(reservation);
    }

    public Reservation updateReservation(Reservation reservation) {
        Optional<Reservation> existingReservation = reservationRepository.findByReservationId(reservation.getReservationId());
        if (existingReservation.isEmpty()) {
            throw new IllegalArgumentException("Reservation not found");
        }

        return reservationRepository.save(reservation);
    }
}
