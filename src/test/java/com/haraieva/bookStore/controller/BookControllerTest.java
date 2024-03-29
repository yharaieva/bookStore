package com.haraieva.bookStore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.haraieva.bookStore.DataBaseTestRule;
import com.haraieva.bookStore.dto.AuthorChangeDto;
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

import java.util.Set;

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

	private static Integer DEFAULT_PAGE_NO = 0;
	private static Integer DEFAULT_PAGE_SIZE = 10;
	private static String DEFAULT_SORT_BY = "id";

	@Test
	@Transactional
	public void testAddTwoBooksAndGetAllBooks() throws Exception {
		AuthorChangeDto author1 = new AuthorChangeDto("testFirstName_1", "testLastName_1");
		AuthorChangeDto author2 = new AuthorChangeDto("testFirstName_2", "testLastName_2");
		createAnAuthor(author1);
		createAnAuthor(author2);

		BookChangeDto book1 = new BookChangeDto("First Book", Set.of(1L));
		BookChangeDto book2 = new BookChangeDto("Second Book", Set.of(2L));

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

		assertThat(service.getBooks(DEFAULT_PAGE_NO, DEFAULT_PAGE_SIZE, DEFAULT_SORT_BY)).hasSize(2);

		mockMvc.perform(get("/books"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.length()").value(2));
	}

	@Test
	@Transactional
	public void testUpdateABook() throws Exception {
		AuthorChangeDto author = new AuthorChangeDto("testFirstName", "testLastName");
		AuthorChangeDto newAuthor = new AuthorChangeDto("testFirstNameUpdated", "testLastNameUpdated");
		createAnAuthor(author);
		createAnAuthor(newAuthor);

		BookChangeDto book = new BookChangeDto("First Book", Set.of(1L));
		BookChangeDto updatedBook = new BookChangeDto("Second Book", Set.of(2L));

		mockMvc.perform(post("/books")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(book))
				)
				.andExpect(status().isOk());

		mockMvc.perform(get("/books/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.title").value(book.getTitle()))
				.andExpect(jsonPath("$.author.id").value(book.getAuthorIds()))
				.andExpect(jsonPath("$.author.firstName").value(author.getFirstName()))
				.andExpect(jsonPath("$.author.lastName").value(author.getLastName()));

		mockMvc.perform(put("/books/1", updatedBook)
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(updatedBook))
				)
				.andExpect(status().isOk());

		mockMvc.perform(get("/books/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.title").value(updatedBook.getTitle()))
				.andExpect(jsonPath("$.author.id").value(updatedBook.getAuthorIds()))
				.andExpect(jsonPath("$.author.firstName").value(newAuthor.getFirstName()))
				.andExpect(jsonPath("$.author.lastName").value(newAuthor.getLastName()));
	}

	@Test
	@Transactional
	public void testAddABookWithEmptyTitleThrowsAnError() throws Exception {
		AuthorChangeDto author = new AuthorChangeDto("testFirstName", "testLastName");
		createAnAuthor(author);

		BookChangeDto book = new BookChangeDto("", Set.of(1L));

		mockMvc.perform(post("/books")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(book))
				)
				.andExpect(status().isBadRequest());
	}

//	@Test
//	@Transactional
//	public void testAddABookWithEmptyAuthorFirstNameThrowsAnError() throws Exception {
//		AuthorChangeDto author = new AuthorChangeDto("", "testLastName");
//
//		BookChangeDto book = new BookChangeDto("A book", author);
//
//		mockMvc.perform(post("/books")
//						.contentType(MediaType.APPLICATION_JSON)
//						.content(objectMapper.writeValueAsString(book))
//				)
//				.andExpect(status().isBadRequest());
//	}
//
//	@Test
//	@Transactional
//	public void testUpdateABookWithEmptyAuthorLastNameThrowsAnError() throws Exception {
//		AuthorChangeDto author = new AuthorChangeDto("testFirstName", "testLastName");
//		AuthorChangeDto updatedAuthor = new AuthorChangeDto("testFirstNameUpdated", "");
//
//		BookChangeDto book = new BookChangeDto("First Book", author);
//		BookChangeDto updatedBook = new BookChangeDto("Second Book", updatedAuthor);
//
//		mockMvc.perform(post("/books")
//						.contentType(MediaType.APPLICATION_JSON)
//						.content(objectMapper.writeValueAsString(book))
//				)
//				.andExpect(status().isOk());
//
//		mockMvc.perform(put("/books/1", updatedBook)
//						.contentType(MediaType.APPLICATION_JSON)
//						.content(objectMapper.writeValueAsString(updatedBook))
//				)
//				.andExpect(status().isBadRequest());
//	}

	private void createAnAuthor (AuthorChangeDto authorChangeDto) throws Exception {
		mockMvc.perform(post("/authors")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(authorChangeDto))
				)
				.andExpect(status().isOk());
	}
}
