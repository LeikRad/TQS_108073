package leikrad.dev.homework1.boundary;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.persistence.EntityNotFoundException;
import leikrad.dev.homework1.data.trip.Trip;
import leikrad.dev.homework1.service.TripManagerService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api")
public class TripController {
    
    private final TripManagerService tripManagerService;
    private static final Logger logger = LoggerFactory.getLogger(TripController.class);

    public TripController(TripManagerService tripManagerService) {
        this.tripManagerService = tripManagerService;
    }

    @PostMapping("/trip")
    public ResponseEntity<Trip> createTrip(@RequestBody Trip trip) {
        try {
            Trip newTrip = tripManagerService.createTrip(trip);
            logger.info("Created trip: {}", newTrip);
            return new ResponseEntity<>(newTrip, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            logger.error("Error creating trip: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/trip")
    public ResponseEntity<List<Trip>> getAllTrips(@RequestParam(required = false) String originCity, @RequestParam(required = false) String destinationCity) {
        if (originCity != null && destinationCity != null) {
            try {
                List<Trip> trips = tripManagerService.getTripsByOriginAndDestinationCity(originCity, destinationCity);
                logger.info("Retrieved trips by cities: {}", trips);
                return new ResponseEntity<>(trips, HttpStatus.OK);
            } catch (EntityNotFoundException e) {
                logger.error("Error retrieving trips by cities: {}", e.getMessage());
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else {
            try {
                List<Trip> trips = tripManagerService.getAllTrips();
                logger.info("Retrieved all trips: {}", trips);
                return new ResponseEntity<>(trips, HttpStatus.OK);
            } catch (EntityNotFoundException e) {
                logger.error("Error retrieving all trips: {}", e.getMessage());
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
    }

    @GetMapping("/trip/{id}")
    public ResponseEntity<Trip> getTripById(@PathVariable Long id) {
        logger.info("Retrieved trip with id: {}", id);
        return tripManagerService.getTripDetails(id)
                .map(trip -> new ResponseEntity<>(trip, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/trip/{id}")
    public ResponseEntity<Trip> updateTrip(@PathVariable Long id, @RequestBody Trip trip) {
        try {
            trip.setTripId(id);
            Trip updatedTrip = tripManagerService.updateTrip(trip);
            logger.info("Updated trip: {}", updatedTrip);
            return new ResponseEntity<>(updatedTrip, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            logger.error("Error updating trip: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/trip/{id}")
    public ResponseEntity<Void> deleteTrip(@PathVariable Long id) {
        try {
            tripManagerService.deleteTrip(id);
            logger.info("Deleted trip with id: {}", id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException e) {
            logger.error("Error deleting trip: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
