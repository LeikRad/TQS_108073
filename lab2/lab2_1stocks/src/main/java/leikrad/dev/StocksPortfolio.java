package leikrad.dev;

import java.util.List;
import java.util.ArrayList;

public class StocksPortfolio {
    private IStockmarketService stockmarket;
    private List<Stock> stocks = new ArrayList<>();

    public StocksPortfolio(IStockmarketService service) {
        this.stockmarket = service;
    }

    public void addStock(Stock stock) {
        stocks.add(stock);
    }

    public double totalValue() {
        double value = 0.0;
        for (Stock stock : stocks) {
            value += stockmarket.lookUpPrice(stock.getLabel()) * stock.getQuantity();
        }
        return value;
    }
}
