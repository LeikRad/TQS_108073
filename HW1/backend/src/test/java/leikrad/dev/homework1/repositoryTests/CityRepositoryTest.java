package leikrad.dev.homework1.repositoryTests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import leikrad.dev.homework1.data.city.*;

@DataJpaTest
public class CityRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CityRepository cityRepository;

    @Test
    @DisplayName("Valid ID should return city")
    void whenFindById_thenReturnCity() {
        // given
        City city = new City("Lisbon");
        entityManager.persistAndFlush(city);

        // when
        City found = cityRepository.findByCityId(city.getCityId()).orElse(null);

        // then
        assertThat(found).isEqualTo(city);
    }

    @Test
    @DisplayName("Invalid ID should return null")
    void whenInvalidId_thenReturnNull() {
        // when
        City fromDb = cityRepository.findByCityId(-99L).orElse(null);

        // then
        assertThat(fromDb).isNull();
    }

    @Test
    @DisplayName("Find all should return all cities")
    void whenFindAll_thenReturnAllCities() {
        City city1 = new City("Lisbon");
        City city2 = new City("Porto");
        City city3 = new City("Faro");

        entityManager.persist(city1);
        entityManager.persist(city2);
        entityManager.persist(city3);
        entityManager.flush();

        // when
        List<City> allCities = cityRepository.findAll();

        // then
        assertThat(allCities).hasSize(3).extracting(City::getCityName).containsOnly(city1.getCityName(), city2.getCityName(), city3.getCityName());
    }

    @Test
    @DisplayName("Delete city by ID")
    void whenDeleteById_thenShouldDeleteCity() {
        // given
        City city = new City("Lisbon");
        entityManager.persistAndFlush(city);

        // when
        cityRepository.deleteByCityId(city.getCityId());

        // then
        assertThat(cityRepository.findByCityId(city.getCityId()).isEmpty());
    }

}