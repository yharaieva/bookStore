package com.haraieva.bookStore.controller;

import com.haraieva.bookStore.DataBaseTestRule;
import com.haraieva.bookStore.dto.BookDto;
import com.haraieva.bookStore.repository.BookRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class BookControllerTest extends DataBaseTestRule {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private BookRepository repository;

	@Test
	@Transactional
	public void testAddTwoBooksAndGetAllBooks() throws Exception {
		BookDto book1 = new BookDto("First Book", "First author");
		BookDto book2 = new BookDto("Second Book", "Second author");

		mockMvc.perform(post("/books", book1))
				.andExpect(status().isOk());
		mockMvc.perform(post("/books", book2))
				.andExpect(status().isOk());

		mockMvc.perform(get("/books"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.length()").value(2));
	}

	@Test
	@Transactional
	public void testUpdateABook() throws Exception {
		BookDto book = new BookDto("First Book", "First author");
		BookDto updatedBook = new BookDto("Second Book", "Second author");

		mockMvc.perform(post("/books", book))
				.andExpect(status().isOk());

		mockMvc.perform(get("/books/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.title").value(book.getTitle()))
				.andExpect(jsonPath("$.author").value(book.getAuthor()));

		mockMvc.perform(put("/books/1", updatedBook))
				.andExpect(status().isOk());

		mockMvc.perform(get("/books/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.title").value(updatedBook.getTitle()))
				.andExpect(jsonPath("$.author").value(updatedBook.getAuthor()));
	}
}
