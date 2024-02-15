package com.haraieva.bookStore.service;

import com.haraieva.bookStore.dto.BookChangeDto;
import com.haraieva.bookStore.dto.BookDto;

import java.util.List;

public interface BookService {
	List<BookDto> getBooks();

	BookDto getBookById(long bookId);

	BookDto addBook(BookChangeDto book);

	BookDto updateBook(Long id, BookChangeDto bookDto);
}
