package leikrad.dev.homework1.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import leikrad.dev.homework1.data.city.*;

@Service
public class CityManagerService {
    
    private CityRepository cityRepository;
    
    public CityManagerService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public List<City> getAllCities() {
        return cityRepository.findAll();
    }

    public Optional<City> getCityDetails(Long cityId) {
        return cityRepository.findByCityId(cityId);
    }

   public City createCity(City city) {
        if (city.getCityId() != null) {
            throw new IllegalArgumentException("City ID must be null");
        }
        
        return cityRepository.save(city);
    }

    public City updateCity(City city) {
        Optional<City> existingCity = cityRepository.findByCityId(city.getCityId());
        if (existingCity.isEmpty()) {
            throw new IllegalArgumentException("City not found");
        }

        return cityRepository.save(city);
    }

    public void deleteCity(Long cityId) {
        cityRepository.deleteByCityId(cityId);
    }
}
