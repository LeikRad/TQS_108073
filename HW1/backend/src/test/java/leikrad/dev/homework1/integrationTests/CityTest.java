package leikrad.dev.homework1.integrationTests;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import leikrad.dev.homework1.data.city.City;
import leikrad.dev.homework1.data.city.CityRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "IT-config.properties")
class CityTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CityRepository cityRepository;

    @AfterEach
    void tearDown() {
        cityRepository.deleteAll();
    }

    @Test
    @DisplayName("Create City")
    void testCreateCity() {
        City city = new City("Lisbon");

        ResponseEntity<City> response = restTemplate.postForEntity("/api/city", city,
                City.class);

        List<City> cities = cityRepository.findAll();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(cities).hasSize(1).extracting(City::getCityName).containsOnly("Lisbon");   
    }

    @Test
    @DisplayName("Create city with invalid id")
    void testCreateCityWithInvalidId() {
        City city = new City("Lisbon");
        city.setCityId(1L);

        ResponseEntity<City> response = restTemplate.postForEntity("/api/city", city,
                City.class);
        
        List<City> cities = cityRepository.findAll();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(cities).isEmpty();
    }

    @Test
    @DisplayName("Get all cities")
    void testGetAllCities() {
        Utils.createTestCity(cityRepository,"Lisbon");
        Utils.createTestCity(cityRepository,"Porto");

        ResponseEntity<List<City>> response = restTemplate.exchange("/api/city", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<City>>() {
                });

        List<City> cities = response.getBody();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(cities).hasSize(2).extracting(City::getCityName).contains("Lisbon", "Porto");
    }

    @Test
    @DisplayName("Get city by id")
    void testGetCityById() {
        City createdCity = Utils.createTestCity(cityRepository, "Lisbon");

        Long id = createdCity.getCityId();

        String url = "/api/city/" + id;

        ResponseEntity<City> response = restTemplate.getForEntity(url, City.class);

        City found = response.getBody();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(found).isNotNull();
        assertThat(found.getCityName()).isEqualTo("Lisbon");
    }

    @Test
    @DisplayName("Get city by id not found")
    void testGetCityByIdNotFound() {
        ResponseEntity<City> response = restTemplate.getForEntity("/api/city/2", City.class);

        System.out.println(response);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("Update city")
    void testUpdateCity() {
        City createdCity = Utils.createTestCity(cityRepository, "Lisbon");

        City city = new City("Porto");

        Long id = createdCity.getCityId();
        String url = "/api/city/" + id;

        restTemplate.put(url, city);

        City updatedCity = cityRepository.findById(id).get();

        assertThat(updatedCity.getCityName()).isEqualTo("Porto");
    }

    @Test
    @DisplayName("Update city not found")
    void testUpdateCityNotFound() {
        City city = new City("Porto");

        HttpEntity<City> requestEntity = new HttpEntity<>(city);
        
        ResponseEntity<City> response = restTemplate.exchange("/api/city/-1", HttpMethod.PUT, requestEntity, City.class);
        
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("Delete city")
    void testDeleteCity() {
        City createdCity = Utils.createTestCity(cityRepository, "Lisbon");

        Long id = createdCity.getCityId();
        String url = "/api/city/" + id;

        ResponseEntity<City> response = restTemplate.exchange(url, HttpMethod.DELETE, null,
                City.class);
        
        List<City> cities = cityRepository.findAll();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(cities).isEmpty();
    }

    @Test
    @DisplayName("Delete city not found")
    void testDeleteCityNotFound() {
        Utils.createTestCity(cityRepository, "Lisbon");

        ResponseEntity<City> response = restTemplate.exchange("/api/city/-1", HttpMethod.DELETE, null,
                City.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
    

}
