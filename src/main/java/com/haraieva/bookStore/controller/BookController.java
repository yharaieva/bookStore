package com.haraieva.bookStore.controller;

import com.haraieva.bookStore.dto.BookDto;
import com.haraieva.bookStore.entity.BookEntity;
import com.haraieva.bookStore.exceptions.ResourceNotFoundException;
import com.haraieva.bookStore.repository.BookRepository;
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

	private final BookRepository repository;

	@Autowired
	public BookController(BookRepository repository) {
		this.repository = repository;
	}

	@GetMapping
	public List<BookEntity> getBooks() {
		return repository.findAll();
	}

	@GetMapping(value = "/{id}", produces = "application/json")
	public BookEntity getBook(@PathVariable long id) {
		return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("There is no book with id: " + id));
	}

	@PostMapping
	@Transactional
	public BookEntity addBook(@RequestBody BookDto book) {
		BookEntity bookEntity = new BookEntity(book.getTitle(), book.getAuthor());
		return repository.save(bookEntity);
	}

	@PutMapping("/{id}")
	@Transactional
	public BookEntity updateBook(@PathVariable Long id, @RequestBody BookDto book){
		if (repository.findById(id).isPresent()) {
			return new BookEntity(id, book.getTitle(), book.getAuthor());
		}
		throw new ResourceNotFoundException("There is no book with id: " + id);
	}
}
