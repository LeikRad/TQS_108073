package leikrad.dev.homework1.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import leikrad.dev.homework1.data.trip.*;

@Service
public class TripManagerService {
    
    private TripRepository tripRepository;
    
    public TripManagerService(TripRepository tripRepository) {
        this.tripRepository = tripRepository;
    }

    public List<Trip> getAllTrips() {
        return tripRepository.findAll();
    }

    public Optional<Trip> getTripDetails(Long tripId) {
        return tripRepository.findByTripId(tripId);
    }

    public List<Trip> getTripsByOriginAndDestinationCity(String originCityName, String destinationCityName) {
        return tripRepository.findTripsByCities(originCityName, destinationCityName);
    }

    @Transactional
    public Trip createTrip(Trip trip) {
        if (trip.getTripId() != null) {
            throw new IllegalArgumentException("Trip ID must be null");
        }

        return tripRepository.save(trip);
    }

    @Transactional
    public Trip updateTrip(Trip trip) {
        Optional<Trip> existingTrip = tripRepository.findByTripId(trip.getTripId());
        if (existingTrip.isEmpty()) {
            throw new EntityNotFoundException("Trip not found");
        }

        return tripRepository.save(trip);
    }

    @Transactional
    public void deleteTrip(Long tripId) {
        Optional<Trip> existingTrip = tripRepository.findByTripId(tripId);
        if (existingTrip.isEmpty()) {
            throw new EntityNotFoundException("Trip not found");
        }
        tripRepository.deleteByTripId(tripId);
    }

}
