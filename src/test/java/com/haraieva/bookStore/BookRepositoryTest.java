package com.haraieva.bookStore;

import com.haraieva.bookStore.model.Book;
import com.haraieva.bookStore.repository.BookRepository;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = { BookStoreApplication.class })
public class BookRepositoryTest {

	@Autowired
	private BookRepository bookRepository;

	static int containerPort = 5432;
	@Container
	static PostgreSQLContainer<?> postgres = TestBookStoreApplication.postgresContainer()
			.withDatabaseName("test")
			.withUsername("root")
			.withPassword("root")
			.withReuse(true)
			.withExposedPorts(containerPort);

	static {
		postgres.start();
	}

	@AfterAll
	static void afterAll() {
		postgres.stop();
	}

	@DynamicPropertySource
	static void configureProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", postgres::getJdbcUrl);
		registry.add("spring.datasource.username", postgres::getUsername);
		registry.add("spring.datasource.password", postgres::getPassword);
	}

	@Test
	@Transactional
	public void testSaveNewBookAndUpdateTheCount() {
		Long initialCount = bookRepository.count();
		int bookId = 1;
		Book book = new Book(bookId, "First Book");

		bookRepository.save(book);

		Long newCount = bookRepository.count();
		Optional<Book> retrievedBook = bookRepository.findById(bookId);

		assertEquals(newCount, initialCount + 1);
		assertEquals(book, retrievedBook.get());
	}
}
