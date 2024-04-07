package leikrad.dev.homework1.data.trip;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.lang.NonNull;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long>{
    
    public Optional<Trip> findByTripId(Long tripId);

    @NonNull
    public List<Trip> findAll();

    public void deleteByTripId(Long tripId);

    @Query("SELECT t FROM Trip t WHERE (:originCityName is null or t.originCity.cityName = :originCityName) and (:destinationCityName is null or t.destinationCity.cityName = :destinationCityName)")
    @NonNull
    public List<Trip> findTripsByCities(@Param("originCityName") String originCityName, @Param("destinationCityName") String destinationCityName);
}
