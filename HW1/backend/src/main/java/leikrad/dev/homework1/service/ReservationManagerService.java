package leikrad.dev.homework1.service;

import java.util.List;
import java.util.Optional;

import leikrad.dev.homework1.data.reservation.*;

public class ReservationManagerService {
    
    private ReservationRepository reservationRepository;

    public ReservationManagerService(ReservationRepository reservationRepository) {}

    public List<Reservation> getAllReservations() {
    }

    public Optional<Reservation> getReservationDetails(Long reservationId) {
    }

    public void deleteReservation(Long reservationId) {
    }

}
