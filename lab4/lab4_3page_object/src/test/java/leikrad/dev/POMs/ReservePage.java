package leikrad.dev.POMs;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ReservePage extends BasePage {

    By chooseFlight = By.cssSelector("input[type='submit']");

    public ReservePage(WebDriver driver, int timeoutSec) {
        this(driver);
        setTimeoutSec(timeoutSec);
    }

    public ReservePage(WebDriver driver) {
        super(driver);
        visit("https://blazedemo.com/reserve.php");
    }

    public PurchasePage chooseFlight() {
        click(chooseFlight);
        // return instance of next page
        return new PurchasePage(driver, timeoutSec);
    }

    public boolean success() {
        // get title of next page
        return getTitle().equals("BlazeDemo Purchase");
    }
}
