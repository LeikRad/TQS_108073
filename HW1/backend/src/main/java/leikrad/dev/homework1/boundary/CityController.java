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
import org.springframework.web.bind.annotation.RestController;

import jakarta.persistence.EntityNotFoundException;
import leikrad.dev.homework1.data.city.City;
import leikrad.dev.homework1.service.CityManagerService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api")
public class CityController {

    private final CityManagerService cityManagerService;
    private static final Logger logger = LoggerFactory.getLogger(CityController.class);

    public CityController(CityManagerService cityManagerService) {
        this.cityManagerService = cityManagerService;
    }

    @PostMapping("/city")
    public ResponseEntity<City> createCity(@RequestBody City city) {
        try {
            City newCity = cityManagerService.createCity(city);
            logger.info("Created city: {}", newCity.getCityName());
            return new ResponseEntity<>(newCity, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            logger.error("Error creating city: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/city")
    public ResponseEntity<List<City>> getAllCities() {
        List<City> cities = cityManagerService.getAllCities();
        logger.info("Retrieved all cities");
        return new ResponseEntity<>(cities, HttpStatus.OK);
    }

    @GetMapping("/city/{cityId}")
    public ResponseEntity<City> getCityDetails(@PathVariable Long cityId) {
        logger.info("Retrieved city details for cityId: {}", cityId);
        return cityManagerService.getCityDetails(cityId)
                .map(city -> new ResponseEntity<>(city, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/city/{cityId}")
    public ResponseEntity<Void> deleteCity(@PathVariable Long cityId) {
        try {
            cityManagerService.deleteCity(cityId);
            logger.info("Deleted city with cityId: {}", cityId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException e) {
            logger.error("Error deleting city: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/city/{cityId}")
    public ResponseEntity<City> updateCity(@PathVariable Long cityId, @RequestBody City city) {
        try {
            city.setCityId(cityId);
            City updatedCity = cityManagerService.updateCity(city);
            logger.info("Updated city with cityId: {}", updatedCity.getCityId());
            return new ResponseEntity<>(updatedCity, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            logger.error("Error updating city: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
