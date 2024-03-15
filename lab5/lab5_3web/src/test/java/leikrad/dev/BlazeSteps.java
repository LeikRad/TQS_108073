package leikrad.dev;

import io.cucumber.java.After;
import io.cucumber.java.ParameterType;

import java.time.LocalDate;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedCondition;


import ch.qos.logback.core.util.Duration;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class BlazeSteps {
    
    private final WebDriver driver = new FirefoxDriver();
    
    @Given("I am on the BlazeDemo home page")
    public void i_am_on_the_blaze_demo_home_page() {
        driver.get("https://blazedemo.com/");
    }

    @When("I write\\/select {string} on the {string} input")
        public void i_select_on_the_input(String value, String input) {
        WebElement element = driver.findElement(By.name(input));
        element.sendKeys(value);
    }

    @When("I click {string}")
    public void i_click_on_the_button(String button) {
        WebElement element = driver.findElement(By.xpath("//input[@type='submit' and @value='" + button + "']"));
        element.click();
    }

    @When("I click Choose This Flight on flight {int}")
    public void i_click_on_the_button_on_flight(Integer flight) {
        List<WebElement> rows = driver.findElements(By.xpath("//tr[.//input[@type='submit']]"));
        WebElement element = null;
        for (WebElement row : rows) {
            WebElement flightNumber = row.findElement(By.xpath(".//td[2]")); // assuming the flight number is in the second td
            if (Integer.parseInt(flightNumber.getText()) == flight) {
                element = row.findElement(By.xpath(".//input[@type='submit']"));
                break;
            }
        }
        if (element == null) {
            throw new RuntimeException("Flight not found");
        }
        element.click();
    }

    @Then("I should be redirected to a page with the title {string}")
    public void i_should_see_in_the_title(String title) {
        try {
            String actualTitle = driver.getTitle();
            assert actualTitle.contains(title);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @After()
    public void closeBrowser() {
        driver.quit();
    }
}
