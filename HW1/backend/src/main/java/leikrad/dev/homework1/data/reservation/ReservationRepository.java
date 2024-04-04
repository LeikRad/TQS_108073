package leikrad.dev.homework1.data.reservation;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long>{

    public Optional<Reservation> findByReservationId(Long reservationId);

    public void deleteByReservationId(Long reservationId);

    @NonNull
    public List<Reservation> findAll();

    public Optional<Reservation> findByUuid(String uuid);
    
}
