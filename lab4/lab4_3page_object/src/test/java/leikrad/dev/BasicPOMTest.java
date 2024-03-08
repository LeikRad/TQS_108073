package leikrad.dev;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.github.bonigarcia.seljup.SeleniumJupiter;

import static org.assertj.core.api.Assertions.assertThat;

import leikrad.dev.POMs.CitySelectionPage;
import leikrad.dev.POMs.PurchasePage;
import leikrad.dev.POMs.ReservePage;

@ExtendWith(SeleniumJupiter.class)
public class BasicPOMTest {

    CitySelectionPage citySelectionPage;
    PurchasePage purchasePage;
    ReservePage reservePage;

    @BeforeEach
    public void setUp(FirefoxDriver driver) {
        citySelectionPage = new CitySelectionPage(driver);
    }

    @Test
    public void test() {
        reservePage = citySelectionPage.with("Boston", "New York");
        assertThat(citySelectionPage.success());
        purchasePage = reservePage.chooseFlight();
        assertThat(reservePage.success());
        purchasePage.with("John Doe", "123 Main St", "Boston", "MA", "12345", "Visa", "1234567890", "12", "2023",
                "John Doe");
        assertThat(purchasePage.success());
    }
}
