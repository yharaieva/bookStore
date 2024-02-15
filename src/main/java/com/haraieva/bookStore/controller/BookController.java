package com.haraieva.bookStore.controller;

import com.haraieva.bookStore.dto.BookChangeDto;
import com.haraieva.bookStore.dto.BookDto;
import com.haraieva.bookStore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

	private final BookService service;

	@Autowired
	public BookController(BookService service) {
		this.service = service;
	}

	@GetMapping
	public List<BookDto> getBooks() {
		return service.getBooks();
	}

	@GetMapping(value = "/{id}", produces = "application/json")
	public BookDto getBook(@PathVariable long id) {
		return service.getBookById(id);
	}

	@PostMapping
	@Transactional
	public BookDto addBook(@RequestBody BookChangeDto book) {
		return service.addBook(book);
	}

	@PutMapping("/{id}")
	@Transactional
	public BookDto updateBook(@PathVariable Long id, @RequestBody BookChangeDto book){
		return service.updateBook(id, book);
	}
}
