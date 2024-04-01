package leikrad.dev.homework1.data.reservation;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.lang.NonNull;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long>{
    
    public Optional<Reservation> findByReservationId(Long reservationID);

    @NonNull
    public List<Reservation> findAll();

    public void deleteByReservationId(Long reservationID);
}
