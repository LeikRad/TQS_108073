package leikrad.dev.homework1.unitTests;

import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import leikrad.dev.homework1.data.currency.*;
import leikrad.dev.homework1.service.CurrencyManagerService;


@ExtendWith(MockitoExtension.class)
class Currency_UnitTest {
    
    @Mock(lenient = true)
    private CurrencyRepository currencyRepository;

    @Mock(lenient = true)
    private RestTemplateBuilder restTemplateBuilder;

    @Mock(lenient = true)
    private RestTemplate restTemplate;

    @InjectMocks
    private CurrencyManagerService currencyManagerService;

    @BeforeEach
    void setUp() throws IOException {
        MockitoAnnotations.openMocks(this);
        
        String apiUrl = "https://v6.exchangerate-api.com/v6/null/latest/EUR";
        Currency currency1 = new Currency("EUR", 1.0);
        Currency currency2 = new Currency("USD", 0.85);
        Currency currency3 = new Currency("GBP", 1.15);
    
        List<Currency> allCurrencies = List.of(currency1, currency2, currency3);

        Mockito.when(restTemplate.getForObject(apiUrl, String.class)).thenReturn(CurrencyAPIResponse.res);
        
        Mockito.when(restTemplateBuilder.build()).thenReturn(restTemplate);
        
        Mockito.when(currencyRepository.findAll()).thenReturn(allCurrencies);
        Mockito.when(currencyRepository.findById(currency1.getId())).thenReturn(Optional.of(currency1));
        Mockito.when(currencyRepository.findById(currency2.getId())).thenReturn(Optional.of(currency2));
        Mockito.when(currencyRepository.findById(currency3.getId())).thenReturn(Optional.of(currency3));
    }

    @Test
    @DisplayName("Test get all currencies")
    void testGetAllCurrencies() {
        Currency currency1 = new Currency("EUR", 1.0);
        Currency currency2 = new Currency("USD", 0.85);
        Currency currency3 = new Currency("GBP", 1.15);

        Iterable<Currency> allCurrencies = currencyManagerService.getAllCurrencies();

        assertThat(allCurrencies).hasSize(3).extracting(Currency::getId).contains(currency1.getId(), currency2.getId(), currency3.getId());
        assertThat(allCurrencies).hasSize(3).extracting(Currency::getEurRate).contains(currency1.getEurRate(), currency2.getEurRate(), currency3.getEurRate());
    }

    @Test
    @DisplayName("Test get currency by id")
    void testGetCurrencyById() {
        Currency currency1 = new Currency("EUR", 1.0);
        
        Currency foundCurrency = currencyManagerService.getCurrencyById(currency1.getId()).get();
        
        assertThat(foundCurrency.getId()).isEqualTo(currency1.getId());
        assertThat(foundCurrency.getEurRate()).isEqualTo(currency1.getEurRate());
    }

    @Test
    @DisplayName("Test get service stats")
    void testGetServiceStats() {
        String stats = "{ \"cacheHits\": 0, \"externalApiRequests\": 0, \"totalRequests\": 0 }";
        
        String serviceStats = currencyManagerService.getServiceStats();
        
        assertThat(serviceStats).isEqualTo(stats);
    }
}
