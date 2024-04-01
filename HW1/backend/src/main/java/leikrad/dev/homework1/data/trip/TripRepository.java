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

    /* These functions have weird names but they're right
     * The function findByOriginCityCityName is supposed to return all trips that have the origin city with the name cityName
     * so originCity has a CityName, that leads to CityCity in the function name
     * DO NOT CHANGE THIS FUNCTION NAME
     */
    @NonNull
    public List<Trip> findByOriginCityCityName(String cityName);

    @NonNull
    public List<Trip> findByDestinationCityCityName(String cityName);

    @NonNull
    public List<Trip> findByOriginCityCityNameAndDestinationCityCityName(String originCityName, String destinationCityName);
}
