package com.haraieva.bookStore.controller;

import com.haraieva.bookStore.dto.BookChangeDto;
import com.haraieva.bookStore.dto.BookDto;
import com.haraieva.bookStore.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
public class BookController {

	private final BookService service;

	@Autowired
	public BookController(BookService service) {
		this.service = service;
	}

	@GetMapping
	public Page<BookDto> getBooks(
			@RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "10") Integer pageSize,
			@RequestParam(defaultValue = "id") String sortBy) {
		return service.getBooks(pageNo, pageSize, sortBy);
	}

	@GetMapping(value = "/{id}", produces = "application/json")
	public BookDto getBook(@PathVariable long id) {
		return service.getBookById(id);
	}

	@PostMapping
	public BookDto addBook(@RequestBody @Valid BookChangeDto book) {
		return service.addBook(book);
	}

	@PutMapping("/{id}")
	public BookDto updateBook(@PathVariable Long id, @RequestBody @Valid BookChangeDto book){
		return service.updateBook(id, book);
	}

	@DeleteMapping("/{id}")
	public void deleteBook(@PathVariable Long id) {
		service.deleteBook(id);
	}
}
