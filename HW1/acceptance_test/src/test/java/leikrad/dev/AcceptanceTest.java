package leikrad.dev;

// Generated by Selenium IDE

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.seljup.SeleniumJupiter;

import org.openqa.selenium.JavascriptExecutor;
import java.util.*;


@ExtendWith(SeleniumJupiter.class)
class AcceptanceTest {
    private Map<String, Object> vars;
    JavascriptExecutor js;
    
    @Test
    void interactiveTest() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--headless");
        ChromeDriver driver = new ChromeDriver(options);
        
        js = (JavascriptExecutor) driver;
        vars = new HashMap<String, Object>();

        driver.get("http://localhost/");
        driver.manage().window().setSize(new org.openqa.selenium.Dimension(1920, 1080));
        driver.manage().timeouts().implicitlyWait(java.time.Duration.ofSeconds(3));

        WebElement cityorigin = driver.findElement(By.id("OriginCity"));
        WebElement citydestination = driver.findElement(By.id("DestinationCity"));
        WebElement currency = driver.findElement(By.id("Currency"));

        cityorigin.click();
        WebElement cityoriginInput = driver.findElement(By.id("Lisboa"));
        cityoriginInput.click();

        citydestination.click();
        WebElement citydestinationInput = driver.findElement(By.id("Porto"));
        citydestinationInput.click();

        currency.click();
        WebElement currencyInput = driver.findElement(By.id("USD"));
        currencyInput.click();

        driver.manage().timeouts().implicitlyWait(java.time.Duration.ofSeconds(2));

        WebElement element = driver.findElement(By.id("Book1"));
    
        
        element.click();

        WebElement personName = driver.findElement(By.name("personName"));
        personName.sendKeys("John Doe");
        WebElement phoneNumber = driver.findElement(By.name("phoneNumber"));
        phoneNumber.sendKeys("123456789");

        WebElement submitButton = driver.findElement(By.cssSelector("button.inline-flex:nth-child(3)"));
        submitButton.click();

        WebElement response = driver.findElement(By.id("uuid"));
        String responseText = response.getText();

        WebElement quitButton = driver.findElement(By.cssSelector(".lucide-x"));
        quitButton.click();

        WebElement reservationButton = driver.findElement(By.id("resButton"));
        reservationButton.click();

        driver.manage().timeouts().implicitlyWait(java.time.Duration.ofSeconds(2));

        WebElement uuid = driver.findElement(By.id("reservation-code"));
        uuid.sendKeys(responseText);

        WebElement searchButton = driver.findElement(By.id("submitRes"));
        searchButton.click();

        WebElement tripCollapse = driver.findElement(By.id("tripCollapse"));
        tripCollapse.click();

        WebElement returnName = driver.findElement(By.id("Name"));
        WebElement returnPhone = driver.findElement(By.id("PhoneNumber"));
        WebElement OriginCity = driver.findElement(By.id("OriginCity"));
        WebElement DestinationCity = driver.findElement(By.id("DestinationCity"));

        String returnNameText = returnName.getText();
        String returnPhoneText = returnPhone.getText();
        String OriginCityText = OriginCity.getText();
        String DestinationCityText = DestinationCity.getText();

        assertThat(DestinationCityText).isEqualTo("Porto");
        assertThat(OriginCityText).isEqualTo("Lisboa");
        assertThat(returnNameText).isEqualTo("John Doe");
        assertThat(returnPhoneText).isEqualTo("123456789");
        driver.quit();
    }
}
