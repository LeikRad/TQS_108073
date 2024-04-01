package leikrad.dev.homework1.service;

import java.util.List;
import java.util.Optional;

import leikrad.dev.homework1.data.city.*;

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

    public void deleteCity(Long cityId) {
        Optional<City> city = cityRepository.findByCityId(cityId);
        if (city.isPresent()) {
            cityRepository.deleteByCityId(cityId);
        }
    }

}
