package com.haraieva.bookStore.service;

import com.haraieva.bookStore.BookStoreApplication;
import com.haraieva.bookStore.DataBaseTestRule;
import com.haraieva.bookStore.dto.AuthorChangeDto;
import com.haraieva.bookStore.dto.BookChangeDto;
import com.haraieva.bookStore.dto.BookDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = { BookStoreApplication.class })
public class BookServiceTest extends DataBaseTestRule {

	@Autowired
	private BookService bookService;

	@Test
	@Transactional
	public void testAddNewBook() {
		BookChangeDto bookDto = new BookChangeDto("First Book", 1L);

		BookDto addedBook = bookService.addBook(bookDto);

		BookDto retrievedBook = bookService.getBookById(addedBook.getId());

		assertEquals(addedBook, retrievedBook);
	}
}
