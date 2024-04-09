package leikrad.dev.homework1.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import leikrad.dev.homework1.data.currency.Currency;
import leikrad.dev.homework1.data.currency.CurrencyRepository;

@Service
public class CurrencyManagerService {
    
    @Value("${api.key}")
    private String apiKey;

    private final Logger logger = LoggerFactory.getLogger(CurrencyManagerService.class);

    private RestTemplate restTemplate;

    private CurrencyRepository currencyRepository;

    private class ServiceStats {
        private int cacheHits;
        private int externalApiRequests;
        private int totalRequests;


        public ServiceStats() {
            cacheHits = 0;
            externalApiRequests = 0;
            totalRequests = 0;
        }

        public void incrementCacheHits() {
            cacheHits++;
        }

        public void incrementCacheMisses() {
            externalApiRequests++;
        }

        public void incrementTotalRequests() {
            totalRequests++;
        }

        public String toString() {
            return "{ \"cacheHits\": " + cacheHits + ", \"externalApiRequests\": " + externalApiRequests + ", \"totalRequests\": " + totalRequests + " }";
        }
    }

    private ServiceStats serviceStats = new ServiceStats();

    public CurrencyManagerService(CurrencyRepository currencyRepository, RestTemplateBuilder restTemplateBuilder) {
        this.currencyRepository = currencyRepository;
        this.restTemplate = restTemplateBuilder.build();
    }

    public Iterable<Currency> getAllCurrencies() {
        try {
            refreshCurrencies();
        } catch (ParseException e) {
            logger.error("Error refreshing currencies: {}", e.getMessage());
            return new ArrayList<>();
        }
        return currencyRepository.findAll();
    }

    public Optional<Currency> getCurrencyById(String code) {
        try {
            refreshCurrencies();
        } catch (ParseException e) {
            logger.error("Error refreshing currencies: {}", e.getMessage());
            return Optional.empty();
        }
        return currencyRepository.findById(code);
    }
    
    public String getServiceStats() {
        return serviceStats.toString();
    }

    private void refreshCurrencies() throws ParseException {
        serviceStats.incrementTotalRequests();
        // check if the currency rates are still in redis
        // if not, make a request to the external API to get the latest currency rates
        logger.info("Checking if currency rates are in the database");
        Currency curr = currencyRepository.findById("EUR").orElse(null);
        if (curr != null) {
            logger.info("{}", currencyRepository.count());
            logger.info("Currency rates are already in the database");
            serviceStats.incrementCacheHits();
            return;
        }
        serviceStats.incrementCacheMisses();
        logger.info("Currency rates are not in the database");
        // make request to external API to get latest currency rates
        // update the currency rates in the database

        String apiUrl = "https://v6.exchangerate-api.com/v6/" + apiKey + "/latest/EUR";
        logger.info("Updating currency rates from {}", apiUrl);
        String response = restTemplate.getForObject(apiUrl, String.class);
        // parse the response and update the currency rates in the database
        logger.info("Response: {}", response);
        JSONObject obj = (JSONObject) new JSONParser().parse(response);
        obj = (JSONObject) obj.get("conversion_rates");
        
        // get keys
        List<String> keys = new ArrayList<>();
        for (Object keyObj : obj.keySet()) {
            String key = (String) keyObj;
            keys.add(key);
        }
        // create currency objects and save them to redis
        for (String key : keys) {
            Currency currency = new Currency();
            double rate;
            if (obj.get(key) instanceof Long value) {
                rate = value.doubleValue();
            } else {
                rate = (double) obj.get(key);
            }
            currency.setEurRate(rate);
            currency.setId(key);
            currencyRepository.save(currency);
        }

        logger.info("Currency rates updated");

    }

}

