package leikrad.dev;

public class Stock {
    private String label;
    private Integer quantity;

    public Stock(String label, Integer quantity) {
        this.label = label;
        this.quantity = quantity;
    }

    public String getLabel() {
        return label;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
