package leikrad.dev.POMs;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PurchasePage extends BasePage {
    By name = By.id("inputName");
    By address = By.id("address");
    By city = By.id("city");
    By state = By.id("state");
    By zipCode = By.id("zipCode");
    By cardType = By.id("cardType");
    By creditCardNumber = By.id("creditCardNumber");
    By creditCardMonth = By.id("creditCardMonth");
    By creditCardYear = By.id("creditCardYear");
    By nameOnCard = By.id("nameOnCard");
    By purchaseBtn = By.cssSelector("input[type='submit']");

    public PurchasePage(WebDriver driver, int timeoutSec) {
        this(driver);
        setTimeoutSec(timeoutSec);
    }

    public PurchasePage(WebDriver driver) {
        super(driver);
        visit("https://blazedemo.com/purchase.php");
    }

    public void with(String name, String address, String city, String state, String zipCode, String cardType,
            String creditCardNumber, String creditCardMonth, String creditCardYear, String nameOnCard) {
        type(this.name, name);
        type(this.address, address);
        type(this.city, city);
        type(this.state, state);
        type(this.zipCode, zipCode);
        type(this.cardType, cardType);
        type(this.creditCardNumber, creditCardNumber);
        type(this.creditCardMonth, creditCardMonth);
        type(this.creditCardYear, creditCardYear);
        type(this.nameOnCard, nameOnCard);
        click(purchaseBtn);
    }

    public boolean success() {
        // get title of next page
        return getTitle().equals("BlazeDemo Confirmation");
    }
}
