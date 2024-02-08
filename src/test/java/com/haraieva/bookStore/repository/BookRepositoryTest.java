package com.haraieva.bookStore.repository;

import com.haraieva.bookStore.BookStoreApplication;
import com.haraieva.bookStore.DataBaseTestRule;
import com.haraieva.bookStore.entity.BookEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = { BookStoreApplication.class })
public class BookRepositoryTest extends DataBaseTestRule {

	@Autowired
	private BookRepository bookRepository;

	@Test
	@Transactional
	public void testSaveNewBookAndUpdateTheCount() {
		long initialCount = bookRepository.count();
		BookEntity bookEntity = new BookEntity("First Book", "Test author");

		bookRepository.save(bookEntity);

		Long newCount = bookRepository.count();
		Optional<BookEntity> retrievedBook = bookRepository.findById(newCount);

		assertEquals(newCount, initialCount + 1);
		assertEquals(bookEntity, retrievedBook.get());
	}
}
