package com.haraieva.bookStore.controller;

import com.haraieva.bookStore.dto.AuthorChangeDto;
import com.haraieva.bookStore.dto.AuthorDto;
import com.haraieva.bookStore.service.AuthorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authors")
public class AuthorController {

	private final AuthorService service;

	@Autowired
	public AuthorController(AuthorService service) {
		this.service = service;
	}

	@GetMapping(value = "/{id}", produces = "application/json")
	public AuthorDto findById(@PathVariable long id) {
		return service.findById(id);
	}

	@PostMapping
	@Transactional
	public AuthorDto addAuthor(@RequestBody @Valid AuthorChangeDto author) {
		return service.addAuthor(author);
	}

	@DeleteMapping("/{id}")
	@Transactional
	public void deleteAuthor(@PathVariable Long id) {
		service.deleteAuthor(id);
	}
}
