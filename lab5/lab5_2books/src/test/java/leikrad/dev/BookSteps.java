package leikrad.dev;

import static java.lang.invoke.MethodHandles.lookup;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.slf4j.LoggerFactory.getLogger;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.DataTableType;
import io.cucumber.java.ParameterType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;


public class BookSteps {

    static final Logger log = getLogger(lookup().lookupClass());

    private BookDatabase bookDatabase;
    List<Book> foundBooks;
    
    @ParameterType("([0-9]{4})-([0-9]{2})-([0-9]{2})")
	public LocalDate iso8601Date(String year, String month, String day){
		return Utils.localDateFromDateParts(year, month, day);
	}

	@DataTableType
	public Book bookEntry(Map<String, String> tableEntry){
		return new Book(
				tableEntry.get("title"),
				tableEntry.get("author"),
				Utils.isoTextToLocalDate( tableEntry.get("published") ) );
	}
    
    @Given("I have the following books in the store")
    public void setup(DataTable books) {
        List<Map<String, String>> rows = books.asMaps(String.class, String.class);
        bookDatabase = new BookDatabase();
        for (Map<String, String> row : rows) {
            Book book = bookEntry(row);
            bookDatabase.addBook(book);
        }
    }

    @When("the customer searches for books published between {iso8601Date} and {iso8601Date}")
    public void searchBooksByYear(LocalDate fromYear, LocalDate toYear) {
        log.info("Searching for books published between {} and {}", fromYear, toYear);
        foundBooks = bookDatabase.findBooksBetweenYears(fromYear, toYear);
    }

    @When("the customer searches for books written by {string}")
    public void searchBooksByAuthor(String author) {
        log.info("Searching for books written by {}", author);
        foundBooks = bookDatabase.findBooksByAuthor(author);
    }


    @When("the customer searches for books with the title {string}")
    public void searchBooksByTitle(String title) {
        log.info("Searching for books with the title {}", title);
        foundBooks = bookDatabase.findBooksByTitle(title);
    }

    @Then("{int} books should have been found")
    public void verifyAmountOfBooks(int amount, DataTable books) {
        assertEquals(amount, foundBooks.size());
        List<Map<String, String>> rows = books.asMaps(String.class, String.class);
        for (int i = 0; i < amount; i++) {
            Book book = foundBooks.get(i);
            Map<String, String> row = rows.get(i);
            Book expectedBook = bookEntry(row);
            assertEquals(expectedBook, book);
        }
    }
}
