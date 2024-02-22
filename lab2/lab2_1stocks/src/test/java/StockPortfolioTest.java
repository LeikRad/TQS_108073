import leikrad.dev.IStockmarketService;
import leikrad.dev.Stock;
import leikrad.dev.StocksPortfolio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
@ExtendWith(MockitoExtension.class)
class StockPortfolioTest {
    @InjectMocks
    StocksPortfolio portfolio;

    @Mock
    IStockmarketService stockmarket;

    @Test
    @DisplayName("Calculate the total stock value")
    void Test_totalValue() {
        when(stockmarket.lookUpPrice("EBAY")).thenReturn(4.0);
        when(stockmarket.lookUpPrice("MSFT")).thenReturn(1.5);

        portfolio.addStock(new Stock("EBAY"), 2);
        portfolio.addStock(new Stock("MSFT"), 4);

        double result = portfolio.totalValue();

        assertEquals(14.0, result);

        verify(stockmarket, atleast(1)).lookUpPrice(anystring());
    }
}
