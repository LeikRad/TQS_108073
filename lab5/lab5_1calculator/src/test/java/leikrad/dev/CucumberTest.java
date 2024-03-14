package leikrad.dev;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("io/github/bonigarcia")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "io.github.bonigarcia")
public class CucumberTest {

}