package leikrad.dev.homework1.unitTests;

import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.verification.VerificationMode;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import leikrad.dev.homework1.data.city.*;
import leikrad.dev.homework1.service.CityManagerService;

@ExtendWith(MockitoExtension.class)
class CityService_UnitTest {
    
    @Mock(lenient = true)
    private CityRepository cityRepository;

    @InjectMocks
    private CityManagerService cityManagerService;

    @SuppressWarnings("null")
    @BeforeEach
    public void setUp() {
        City city1 = new City("Lisbon");
        City city2 = new City("Porto");
        City city3 = new City("Faro");

        city1.setCityId(1L);
        city2.setCityId(2L);
        city3.setCityId(3L);

        List<City> allCities = List.of(city1, city2, city3);

        Mockito.when(cityRepository.findAll()).thenReturn(allCities);
        Mockito.when(cityRepository.findByCityId(city1.getCityId())).thenReturn(Optional.of(city1));
        Mockito.when(cityRepository.findByCityId(city2.getCityId())).thenReturn(Optional.of(city2));
        Mockito.when(cityRepository.findByCityId(city3.getCityId())).thenReturn(Optional.of(city3));
        Mockito.when(cityRepository.findByCityId(-1L)).thenReturn(Optional.empty());
    }

    @Test
    @DisplayName("Test get all cities")
    void testGetAllCities() {
        City city1 = new City("Lisbon");
        City city2 = new City("Porto");
        City city3 = new City("Faro");

        city1.setCityId(1L);
        city2.setCityId(2L);
        city3.setCityId(3L);

        List<City> allCities = cityManagerService.getAllCities();

        verifyFindAllCitiesIsCalledOnce();
        assertThat(allCities).hasSize(3).extracting(City::getCityName).contains(city1.getCityName(), city2.getCityName(), city3.getCityName());
    }

    @Test
    @DisplayName("Test get city by id")
    void testGetCityDetails() {
        City city1 = new City("Lisbon");
        city1.setCityId(1L);

        City city = cityManagerService.getCityDetails(city1.getCityId()).orElse(null);

        verifyFindCityByIdIsCalledOnce();
        assertThat(city.getCityId()).isEqualTo(city1.getCityId());
        assertThat(city.getCityName()).isEqualTo(city1.getCityName());
    }

    @Test
    @DisplayName("Test get city by id with invalid id")
    void testGetCityDetailsWithInvalidId() {
        City city = new City("Lisbon");
        city.setCityId(1L);

        City found = cityManagerService.getCityDetails(-1L).orElse(null);

        verifyFindCityByIdIsCalledOnce();
        assertThat(found).isNull();
    }

    @Test
    @DisplayName("Test delete city")
    void whenValidId_thenCityShouldBeDeleted() {
        City city1 = new City("Lisbon");
        city1.setCityId(1L);

        cityManagerService.deleteCity(city1.getCityId());

        verifyDeleteCityByIdIsCalledOnce();
    }

    @Test
    @DisplayName("Test delete city with invalid id")
    void whenInvalidId_thenCityShouldNotBeDeleted() {
        City city = new City("Lisbon");
        city.setCityId(1L);

        cityManagerService.deleteCity(-1L);

        verifyDeleteCityByIdWasntCalled();
    }
    
    private void verifyFindAllCitiesIsCalledOnce() {
        Mockito.verify(cityRepository, VerificationModeFactory.times(1)).findAll();
    }

    private void verifyFindCityByIdIsCalledOnce() {
        Mockito.verify(cityRepository, VerificationModeFactory.times(1)).findByCityId(Mockito.anyLong());
    }
    
    private void verifyDeleteCityByIdIsCalledOnce() {
        Mockito.verify(cityRepository, VerificationModeFactory.times(1)).deleteByCityId(Mockito.anyLong());
    }

    private void verifyDeleteCityByIdWasntCalled() {
        Mockito.verify(cityRepository, VerificationModeFactory.times(0)).deleteByCityId(Mockito.anyLong());
    }
}
