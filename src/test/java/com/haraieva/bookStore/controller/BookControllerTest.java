package com.haraieva.bookStore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.haraieva.bookStore.DataBaseTestRule;
import com.haraieva.bookStore.dto.AuthorDto;
import com.haraieva.bookStore.dto.BookChangeDto;
import com.haraieva.bookStore.service.BookService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc(addFilters = false)
public class BookControllerTest extends DataBaseTestRule {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private BookService service;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	@Transactional
	public void testAddTwoBooksAndGetAllBooks() throws Exception {
		AuthorDto author1 = new AuthorDto("testFirstName_1", "testLastName_1");
		AuthorDto author2 = new AuthorDto("testFirstName_2", "testLastName_2");

		BookChangeDto book1 = new BookChangeDto("First Book", author1);
		BookChangeDto book2 = new BookChangeDto("Second Book", author2);

		mockMvc.perform(post("/books")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(book1))
				)
				.andExpect(status().isOk());

		mockMvc.perform(post("/books")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(book2))
				)
				.andExpect(status().isOk());

		assertThat(service.getBooks()).hasSize(2);

		mockMvc.perform(get("/books"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.length()").value(2));
	}

	@Test
	@Transactional
	public void testUpdateABook() throws Exception {
		AuthorDto author = new AuthorDto("testFirstName", "testLastName");
		AuthorDto updatedAuthor = new AuthorDto("testFirstNameUpdated", "testLastNameUpdated");

		BookChangeDto book = new BookChangeDto("First Book", author);
		BookChangeDto updatedBook = new BookChangeDto("Second Book", updatedAuthor);

		mockMvc.perform(post("/books")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(book))
				)
				.andExpect(status().isOk());

		mockMvc.perform(get("/books/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.title").value(book.getTitle()))
				.andExpect(jsonPath("$.author.firstName").value(book.getAuthor().getFirstName()))
				.andExpect(jsonPath("$.author.lastName").value(book.getAuthor().getLastName()));

		mockMvc.perform(put("/books/1", updatedBook)
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(updatedBook))
				)
				.andExpect(status().isOk());

		mockMvc.perform(get("/books/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.title").value(updatedBook.getTitle()))
				.andExpect(jsonPath("$.author.firstName").value(updatedBook.getAuthor().getFirstName()))
				.andExpect(jsonPath("$.author.lastName").value(updatedBook.getAuthor().getLastName()));
	}

	@Test
	@Transactional
	public void testAddABookWithEmptyTitleThrowsAnError() throws Exception {
		AuthorDto author = new AuthorDto("testFirstName", "testLastName");
		BookChangeDto book = new BookChangeDto("", author);

		mockMvc.perform(post("/books")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(book))
				)
				.andExpect(status().isBadRequest());
	}

	@Test
	@Transactional
	public void testAddABookWithEmptyAuthorFirstNameThrowsAnError() throws Exception {
		AuthorDto author = new AuthorDto("", "testLastName");
		BookChangeDto book = new BookChangeDto("A book", author);

		mockMvc.perform(post("/books")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(book))
				)
				.andExpect(status().isBadRequest());
	}

	@Test
	@Transactional
	public void testUpdateABookWithEmptyAuthorLastNameThrowsAnError() throws Exception {
		AuthorDto author = new AuthorDto("testFirstName", "testLastName");
		AuthorDto updatedAuthor = new AuthorDto("testFirstNameUpdated", "");

		BookChangeDto book = new BookChangeDto("First Book", author);
		BookChangeDto updatedBook = new BookChangeDto("Second Book", updatedAuthor);

		mockMvc.perform(post("/books")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(book))
				)
				.andExpect(status().isOk());

		mockMvc.perform(put("/books/1", updatedBook)
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(updatedBook))
				)
				.andExpect(status().isBadRequest());
	}
}
