package leikrad.dev.homework1;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

@SpringBootTest
class Homework1ApplicationTests {

	@Test
	@DisplayName("Always Pass Test For Debugging")
	void testAlwaysPass() {
		Assertions.assertTrue(true, "This test should always pass");
	}

}
