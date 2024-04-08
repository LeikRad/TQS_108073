package leikrad.dev.homework1.controllerTests;

import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.http.MediaType;

// Static imports
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;


import leikrad.dev.homework1.boundary.CurrencyController;
import leikrad.dev.homework1.data.currency.*;
import leikrad.dev.homework1.service.CurrencyManagerService;

@WebMvcTest(CurrencyController.class)
class CurrencyControllerTest {
    
    @Autowired
    private MockMvc mvc;

    @MockBean
    private CurrencyManagerService currencyManagerService;

    @Test
    @DisplayName("Get all currencies")
    void testGetAllCurrencies() throws Exception {
        Currency currency1 = new Currency("EUR", 1.0);
        Currency currency2 = new Currency("USD", 0.85);
        Currency currency3 = new Currency("GBP", 1.15);

        Iterable<Currency> allCurrencies = List.of(currency1, currency2, currency3);

        when(currencyManagerService.getAllCurrencies()).thenReturn(allCurrencies);

        mvc.perform(get("/api/currencies")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(3)))
            .andExpect(jsonPath("$[0].id", is("EUR")))
            .andExpect(jsonPath("$[1].id", is("USD")))
            .andExpect(jsonPath("$[2].id", is("GBP")));

        verify(currencyManagerService, times(1)).getAllCurrencies();
    }

    @Test
    @DisplayName("Get currency by code")
    void testGetCurrencyByCode() throws Exception {
        Currency currency = new Currency("EUR", 1.0);

        when(currencyManagerService.getCurrencyById("EUR")).thenReturn(java.util.Optional.of(currency));

        mvc.perform(get("/api/currencies/EUR")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is("EUR")))
            .andExpect(jsonPath("$.eurRate", is(1.0)));

        verify(currencyManagerService, times(1)).getCurrencyById("EUR");
    }
}
