package leikrad.dev.POMs;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CitySelectionPage extends BasePage {
    By departureCity = By.name("fromPort");

    By destinationCity = By.name("toPort");

    By submitButton = By.cssSelector("input[type='submit']");

    public CitySelectionPage(WebDriver driver, int timeoutSec) {
        this(driver);
        setTimeoutSec(timeoutSec);
    }

    public CitySelectionPage(WebDriver driver) {
        super(driver);
        visit("https://blazedemo.com/");
    }

    public ReservePage with(String departure, String destination) {
        type(departureCity, departure);
        type(destinationCity, destination);
        click(submitButton);
        // return instance of next page
        return new ReservePage(driver, timeoutSec);
    }

    public boolean success() {
        // get title of next page
        return getTitle().equals("BlazeDemo - reserve");
    }
}
