package leikrad.dev.homework1.data.trip;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.lang.NonNull;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long>{
    
    public Optional<Trip> findByTripId(Long tripID);

    @NonNull
    public List<Trip> findAll();

    public void deleteByTripId(Long tripID);

    @NonNull
    public List<Trip> findByOriginCityCityName(String cityName);

    @NonNull
    public List<Trip> findByDestinationCityCityName(String cityName);

    @NonNull
    public List<Trip> findByOriginCityCityNameAndDestinationCityCityName(String originCityName, String destinationCityName);
}
