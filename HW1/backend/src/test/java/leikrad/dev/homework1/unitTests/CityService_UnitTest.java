package leikrad.dev.homework1.unitTests;

import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
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

        verifyFindAllIsCalledOnce();
        assertThat(allCities).hasSize(3).extracting(City::getCityName).contains(city1.getCityName(), city2.getCityName(), city3.getCityName());
    }

    @Test
    @DisplayName("Test get city by id")
    void testGetCityDetails() {
        City city = new City("Lisbon");
        city.setCityId(1L);

        City found = cityManagerService.getCityDetails(1L).orElse(null);

        verifyFindByCityIdIsCalledOnce();
        assertThat(found.getCityId()).isEqualTo(city.getCityId());
        assertThat(found.getCityName()).isEqualTo(city.getCityName());
    }

    @Test
    @DisplayName("Test get city by id with invalid id")
    void testGetCityDetailsWithInvalidId() {
        City found = cityManagerService.getCityDetails(-1L).orElse(null);

        verifyFindByCityIdIsCalledOnce();
        assertThat(found).isNull();
    }

    @Test
    @DisplayName("Test delete city")
    void testDeleteCity() {
        Long cityId = 1L;

        cityManagerService.deleteCity(cityId);

        verifyDeleteByCityIdIsCalledOnce();
    }

    @Test
    @DisplayName("Test create city")
    void testCreateCity() {
        City actualCity = new City("Lisbon");
        City actualCreatedCity = new City("Lisbon");
        
        actualCreatedCity.setCityId(1L);
        
        Mockito.when(cityRepository.save(actualCity)).thenReturn(actualCreatedCity);

        City createdCity = cityManagerService.createCity(actualCity);

        assertThat(createdCity).isNotNull().isEqualTo(actualCreatedCity);
        verifySaveIsCalledOnce();
    }

    @Test
    @DisplayName("Test update city")
    void testUpdateCity() {
        City actualCity = new City("Lisbon");
        City actualCreatedCity = new City("Lisbon");
        City actualCityUpdate = new City("Porto");

        actualCreatedCity.setCityId(1L);
        actualCityUpdate.setCityId(1L);
        
        Mockito.when(cityRepository.save(actualCity)).thenReturn(actualCreatedCity);
        Mockito.when(cityRepository.save(actualCityUpdate)).thenReturn(actualCityUpdate);

        cityManagerService.createCity(actualCity);
        City updatedCity = cityManagerService.updateCity(actualCityUpdate);

        assertThat(updatedCity).isNotNull().isEqualTo(actualCityUpdate);
        verifySaveIsCalledTwice();
    }

    @Test
    @DisplayName("Test update city with invalid id")
    void testUpdateCityWithInvalidId() {
        City actualCity = new City("Lisbon");
        City actualCreatedCity = new City("Lisbon");
        City actualCityUpdate = new City("Porto");

        actualCreatedCity.setCityId(1L);
        actualCityUpdate.setCityId(-1L);

        Mockito.when(cityRepository.save(actualCity)).thenReturn(actualCreatedCity);
        Mockito.when(cityRepository.save(actualCityUpdate)).thenReturn(actualCityUpdate);

        cityManagerService.createCity(actualCity);

        Exception exception = org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class, () -> {
            cityManagerService.updateCity(actualCityUpdate);
        });

        verifySaveIsCalledOnce();
        assertThat(exception).isInstanceOf(IllegalArgumentException.class).hasMessage("City not found");
    }

    @Test
    @DisplayName("Test create with invalid city")
    void testCreateWithInvalidCity() {
        City actualCity = new City("");

        actualCity.setCityId(1L);

        Exception exception = org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class, () -> {
            cityManagerService.createCity(actualCity);
        });

        verifySaveIsntCalled();
        assertThat(exception).isInstanceOf(IllegalArgumentException.class).hasMessage("City ID must be null");
    }

    private void verifyFindAllIsCalledOnce() {
        Mockito.verify(cityRepository, VerificationModeFactory.times(1)).findAll();
    }

    private void verifyFindByCityIdIsCalledOnce() {
        Mockito.verify(cityRepository, VerificationModeFactory.times(1)).findByCityId(Mockito.anyLong());
    }
    
    private void verifyDeleteByCityIdIsCalledOnce() {
        Mockito.verify(cityRepository, VerificationModeFactory.times(1)).deleteByCityId(Mockito.anyLong());
    }

    private void verifySaveIsCalledOnce() {
        Mockito.verify(cityRepository, VerificationModeFactory.times(1)).save(Mockito.any(City.class));
    }

    private void verifySaveIsCalledTwice() {
        Mockito.verify(cityRepository, VerificationModeFactory.times(2)).save(Mockito.any(City.class));
    }
    
    private void verifySaveIsntCalled() {
        Mockito.verify(cityRepository, VerificationModeFactory.times(0)).save(Mockito.any(City.class));
    }

}
