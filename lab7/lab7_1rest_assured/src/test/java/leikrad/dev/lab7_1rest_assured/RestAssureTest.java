package leikrad.dev.lab7_1rest_assured;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

class RestAssureTest {
    
    @Test
    @DisplayName("The endpoint to list all ToDos is available (status code 200)")
    void testListAllToDos() {
        given().
            when().
                get("https://jsonplaceholder.typicode.com/todos").
            then().
                assertThat().
                statusCode(200);
    }

    @Test
    @DisplayName("When querying for ToDo #4, the API returns an object with title “et porro tempora”")
    void testToDo4Title() {
        given().
            when().
                get("https://jsonplaceholder.typicode.com/todos/4").
            then().
                assertThat().
                body("title", equalTo("et porro tempora"));
    }

    @Test
    @DisplayName("When listing all “todos”, you get id #198 and #199 in the JSON results")
    void testToDo198And199() {
        given().
            when().
                get("https://jsonplaceholder.typicode.com/todos").
            then().
                assertThat().
                body("id", hasItems(198, 199));
    }

    @Test
    @DisplayName("When listing all “todos”, you the results in less than 2secs.")
    void testResponseTime() {
        given().
            when().
                get("https://jsonplaceholder.typicode.com/todos").
            then().
                assertThat().
                time(lessThan(2000L));
    }
}
