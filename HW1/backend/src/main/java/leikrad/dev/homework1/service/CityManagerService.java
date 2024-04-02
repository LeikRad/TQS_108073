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

    public City createCity(City city) {
        if (city.getCityId() != null) {
            city.setCityId(null);
        }

        return cityRepository.save(city);
    }

    public City updateCity(City city) {
        if (city.getCityId() == null) {
            return null;
        }
        return cityRepository.save(city);
    }

}
