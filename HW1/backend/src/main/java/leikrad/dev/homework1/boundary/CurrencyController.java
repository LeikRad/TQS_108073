package leikrad.dev.homework1.boundary;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import leikrad.dev.homework1.data.currency.Currency;
import leikrad.dev.homework1.service.CurrencyManagerService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/currencies")
public class CurrencyController {

    private final CurrencyManagerService currencyService;
    private static final Logger logger = LoggerFactory.getLogger(CurrencyController.class);

    public CurrencyController(CurrencyManagerService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping("")
    public ResponseEntity<Iterable<Currency>> getAllCurrencies() {
        Iterable<Currency> currencies = currencyService.getAllCurrencies();
        logger.info("Retrieved all currencies");
        return new ResponseEntity<>(currencies, HttpStatus.OK);
    }

    @GetMapping("/{currencyId}")
    public ResponseEntity<Currency> getCurrencyById(@PathVariable String currencyId) {
        logger.info("Retrieved currency details for currencyId: {}", currencyId);
        return currencyService.getCurrencyById(currencyId)
                .map(currency -> new ResponseEntity<>(currency, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
