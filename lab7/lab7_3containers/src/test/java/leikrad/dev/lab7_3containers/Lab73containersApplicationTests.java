package leikrad.dev.lab7_3containers;


import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import leikrad.dev.lab7_3containers.entities.*;

@Testcontainers
@SpringBootTest
class Lab73containersApplicationTests {

	@Container
	private static final PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:13")
			.withDatabaseName("test")
			.withUsername("test")
			.withPassword("test");

	@Autowired
	private BookRepository bookRepository;

	@DynamicPropertySource
	static void properties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
		registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
		registry.add("spring.datasource.password", 	postgreSQLContainer::getPassword);
	}

	@Test
	void testCreate() {
		Book book = new Book();
		book.setTitle("Test0");
		book.setAuthor("Test0");
		book.setGenre("Test0");
		book.setYear(2021);
		book.setPrice(19.90);
		bookRepository.save(book);

		Book found = bookRepository.findById(book.getId()).orElse(null);

		assertThat(found).isNotNull();
	}

	@Test
	void testUpdate() {
		Book book = new Book();
		book.setTitle("Test");
		book.setAuthor("Test");
		book.setGenre("Test");
		book.setYear(2021);
		book.setPrice(19.90);
		bookRepository.save(book);

		book.setTitle("Test5");
		bookRepository.save(book);

		assertThat(bookRepository.findById(book.getId()).get().getTitle()).isEqualTo("Test5");
	}

	@Test
	void testDelete() {
		Book book = new Book();
		book.setTitle("Test");
		book.setAuthor("Test");
		book.setGenre("Test");
		book.setYear(2021);
		book.setPrice(19.90);
		bookRepository.save(book);

		bookRepository.delete(book);

		assertThat(bookRepository.findById(book.getId())).isEmpty();
	}

	@Test
	void testFindAll() {
		Book book = new Book();
		book.setTitle("Test1");
		book.setAuthor("Test1");
		book.setGenre("Test1");
		book.setYear(2021);
		book.setPrice(19.90);
		bookRepository.save(book);

		assertThat(bookRepository.findAll()).isNotEmpty();
	}

	@Test
	void testFindById() {
		Book book = new Book();
		book.setTitle("Test2");
		book.setAuthor("Test2");
		book.setGenre("Test2");
		book.setYear(2021);
		book.setPrice(19.90);
		bookRepository.save(book);

		Book found = bookRepository.findById(book.getId()).orElse(null);

		assertThat(found.getId()).isEqualTo(book.getId());
		assertThat(found.getTitle()).isEqualTo(book.getTitle());
	}

	@Test
	void testFindByTitle() {
		Book book = new Book();
		book.setTitle("Test3");
		book.setAuthor("Test3");
		book.setGenre("Test3");
		book.setYear(2021);
		book.setPrice(19.90);
		bookRepository.save(book);

		Book found = bookRepository.findByTitle("Test3").orElse(null);

		assertThat(found.getTitle()).isEqualTo(book.getTitle());
		assertThat(found.getId()).isEqualTo(book.getId());
	}
}
