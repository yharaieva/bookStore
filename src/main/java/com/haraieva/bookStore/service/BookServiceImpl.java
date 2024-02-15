package com.haraieva.bookStore.service;

import com.haraieva.bookStore.dto.BookChangeDto;
import com.haraieva.bookStore.dto.BookDto;
import com.haraieva.bookStore.entity.BookEntity;
import com.haraieva.bookStore.exceptions.ResourceNotFoundException;
import com.haraieva.bookStore.mapper.BookMapper;
import com.haraieva.bookStore.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

	private final BookRepository repository;
	private final BookMapper mapper;

	@Autowired
	public BookServiceImpl(BookRepository repository, BookMapper mapper) {
		this.repository = repository;
		this.mapper = mapper;
	}

	@Override
	public List<BookDto> getBooks() {
		return repository.findAll().stream()
				.map(mapper::mapBookEntityToBookDto)
				.collect(Collectors.toList());
	}

	@Override
	public BookDto getBookById(long bookId) {
		BookEntity bookEntity = repository.findById(bookId)
				.orElseThrow(() -> new ResourceNotFoundException("There is no book with id: " + bookId));

		return mapper.mapBookEntityToBookDto(bookEntity);
	}

	@Override
	@Transactional
	public BookDto addBook(BookChangeDto book) {
		BookEntity bookEntity = mapper.mapBookChangeDtoToBookEntity(book);

		repository.save(bookEntity);

		return mapper.mapBookEntityToBookDto(bookEntity);
	}

	@Override
	@Transactional
	public BookDto updateBook(Long id, BookChangeDto bookDto) {
		BookEntity bookEntity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("There is no book with id: " + id));

		bookEntity.setTitle(bookDto.getTitle());
		bookEntity.setAuthor(bookDto.getAuthor());

		return mapper.mapBookEntityToBookDto(bookEntity);
	}
}
