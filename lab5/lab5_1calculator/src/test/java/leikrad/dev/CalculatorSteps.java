package leikrad.dev;

import static java.lang.invoke.MethodHandles.lookup;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.slf4j.LoggerFactory.getLogger;

import org.slf4j.Logger;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CalculatorSteps {

    static final Logger log = getLogger(lookup().lookupClass());

    private Calculator calc;
    private Exception exception;

    @Given("a calculator I just turned on")
    public void setup() {
        calc = new Calculator();
    }

    @When("I enter {int}, {int} and +")
    public void add(int arg1, int arg2) {
        log.debug("Adding {} and {}", arg1, arg2);
        calc.push(arg1);
        calc.push(arg2);
        calc.push("+");
    }

    @When("I enter {int}, {int} and -")
    public void substract(int arg1, int arg2) {
        log.debug("Substracting {} and {}", arg1, arg2);
        calc.push(arg1);
        calc.push(arg2);
        calc.push("-");
    }


    @When("I enter {int}, {int} and *")
    public void multiply(int arg1, int arg2) {
        log.debug("Multiplying {} and {}", arg1, arg2);
        calc.push(arg1);
        calc.push(arg2);
        calc.push("*");
    }

    @When("I enter {int}, {int} and \\/")
    public void divide(int arg1, int arg2) {
        log.debug("Dividing {} and {}", arg1, arg2);
        calc.push(arg1);
        calc.push(arg2);
        calc.push("/");
    }

    @When("I enter {int} and \\/")
    public void divide(int arg1) {
        log.debug("Dividing {} by 2", arg1);
        try {
            calc.push(arg1);
            calc.push("/");
        } catch (Exception e) {
            this.exception = e;
        }    
    }

    @Then("an error should be thrown")
    public void an_error_should_be_thrown() {
        log.debug("An error should be thrown");
        assertEquals(IllegalArgumentException.class, exception.getClass());
    }

    @Then("the result is {double}")
    public void the_result_is(double expected) {
        Number value = calc.value();
        log.debug("Result: {} (expected {})", value, expected);
        assertEquals(expected, value);
    }

}