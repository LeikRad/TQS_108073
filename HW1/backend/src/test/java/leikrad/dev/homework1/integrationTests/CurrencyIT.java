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
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import leikrad.dev.homework1.data.currency.*;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "IT-config.properties")
class CurrencyIT {
    
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CurrencyRepository currencyRepository;

    @AfterEach
    void tearDown() {
        currencyRepository.deleteAll();
    }

    @Test
    @DisplayName("GET /currencies should return 200 OK")
    void testGetAllCurrencies() {
        // given

        // when
        ResponseEntity<List<Currency>> response = restTemplate.exchange(
            "/api/currencies",
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<List<Currency>>() {}
        );
        
        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).hasSize(162);
    }

    @Test
    @DisplayName("GET /currencies/{code} should return 200 OK")
    void testGetCurrencyById() {
        // given
        Currency currency = new Currency("USD", 1.0);
        // when
        ResponseEntity<Currency> response = restTemplate.exchange(
            "/api/currencies/" + currency.getId(),
            HttpMethod.GET,
            null,
            Currency.class
        );

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getId()).isEqualTo(currency.getId());
    } 

    @Test
    @DisplayName("GET /currencies/stats should return 200 OK")
    void testGetServiceStats() {
        // given

        // when
        ResponseEntity<String> response = restTemplate.exchange(
            "/api/currencies/stats",
            HttpMethod.GET,
            null,
            String.class
        );

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
