package leikrad.dev;

import static java.lang.invoke.MethodHandles.lookup;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.slf4j.LoggerFactory.getLogger;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.it.Data;


public class BookSteps {

    static final Logger log = getLogger(lookup().lookupClass());

    private BookDatabase bookDatabase;
    

    @Given("I have the following books in the store")
    public void setup(DataTable books) {
        List<Map<String, String>> rows = books.asMaps(String.class, String.class);
        bookDatabase = new BookDatabase();
        for (Map<String, String> row : rows) {
            String title = row.get("title");
            String author = row.get("author");
            String year = row.get("year");
            bookDatabase.addBook(title, author, year);
        }
    }
}
