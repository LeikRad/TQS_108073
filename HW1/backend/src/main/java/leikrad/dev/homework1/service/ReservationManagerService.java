package leikrad.dev.homework1.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
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

    @Transactional
    public void deleteReservation(Long reservationId) {
        Optional<Reservation> existingReservation = reservationRepository.findByReservationId(reservationId);
        if (existingReservation.isEmpty()) {
            throw new EntityNotFoundException("Reservation not found");
        }
        reservationRepository.deleteByReservationId(reservationId);
    }

    public Optional<Reservation> getReservationByUuid(String uuid) {
        return reservationRepository.findByUuid(uuid);
    }

    @Transactional
    public Reservation createReservation(Reservation reservation) {
        if (reservation.getReservationId() != null) {
            throw new IllegalArgumentException("Reservation ID must be null");
        }
    
        if (reservation.getUuid() != null) {
            throw new IllegalArgumentException("UUID must be null");
        }

        String uuid = java.util.UUID.randomUUID().toString();
        reservation.setUuid(uuid);
        
        return reservationRepository.save(reservation);
    }

    @Transactional
    public Reservation updateReservation(Reservation reservation) {
        Optional<Reservation> existingReservation = reservationRepository.findByReservationId(reservation.getReservationId());
        if (existingReservation.isEmpty()) {
            throw new EntityNotFoundException("Reservation not found");
        }

        return reservationRepository.save(reservation);
    }
}
