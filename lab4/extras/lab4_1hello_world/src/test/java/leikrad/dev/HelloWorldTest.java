package leikrad.dev;

import static java.lang.invoke.MethodHandles.lookup;
import static org.assertj.core.api.Assertions.assertThat;
import static org.slf4j.LoggerFactory.getLogger;

import java.net.URI;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

@ExtendWith(SeleniumJupiter.class)
class HelloWorldTest {

    static final Logger log = getLogger(lookup().lookupClass());

    @TestFactory
    Collection<DynamicTest> dynamicTests() {
        return Arrays.asList(
                dynamicTest("test with Firefox", () -> test_refactor("firefox")),
                dynamicTest("test with Chrome", () -> test_refactor("chrome")),
                dynamicTest("test with Edge", () -> test_refactor("MicrosoftEdge")));
    }

    void test_refactor(String Browser) {
        // Setup
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName(Browser);
        URL url = null;
        try {
            URI uri = new URI("http://localhost:4444/wd/hub");
            url = uri.toURL();
        } catch (Exception e) {
            log.error("Error: {}", e);
            assertThat(false).isTrue();
        }
        WebDriver driver = new RemoteWebDriver(url, capabilities);

        // Exercise
        String sutUrl = "https://bonigarcia.dev/selenium-webdriver-java/";
        driver.get(sutUrl);
        String title = driver.getTitle();
        log.debug("The title of {} is {} on browser {}", sutUrl, title, Browser);

        // Verify
        assertThat(title).isEqualTo("Hands-On Selenium WebDriver with Java");
        log.debug("Success on browser {}", Browser);
        // TearDown
        driver.quit();
    }

}