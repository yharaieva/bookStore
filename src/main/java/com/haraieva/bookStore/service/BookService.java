package com.haraieva.bookStore.service;

import com.haraieva.bookStore.dto.BookChangeDto;
import com.haraieva.bookStore.dto.BookDto;
import com.haraieva.bookStore.entity.AuthorEntity;
import com.haraieva.bookStore.entity.BookEntity;
import com.haraieva.bookStore.exceptions.ResourceNotFoundException;
import com.haraieva.bookStore.loggers.Logger;
import com.haraieva.bookStore.mapper.BookMapper;
import com.haraieva.bookStore.repository.AuthorRepository;
import com.haraieva.bookStore.repository.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@AllArgsConstructor
public class BookService {

	private final AuthorRepository authorRepository;
	private final BookRepository repository;
	private final BookMapper bookMapper;
	private final Logger logger;

	public Page<BookDto> getBooks(Integer pageNo, Integer pageSize, String sortBy) {
		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

		return repository.findAll(paging)
				.map(bookMapper::mapBookEntityToBookDto);
	}

	public BookDto getBookById(long bookId) {
		BookEntity bookEntity = findByIdOrThrow(bookId);

		return bookMapper.mapBookEntityToBookDto(bookEntity);
	}

	@Transactional
	public BookDto addBook(BookChangeDto book) {
		BookEntity bookEntity = bookMapper.mapBookChangeDtoToBookEntity(book);

		Set<AuthorEntity> authors = Set.copyOf(authorRepository.findAllById(book.getAuthorIds()));
		bookEntity.setAuthors(authors);

		repository.save(bookEntity);

		return bookMapper.mapBookEntityToBookDto(bookEntity);
	}

	@Transactional
	public BookDto updateBook(Long id, BookChangeDto book) {
		BookEntity bookEntity = findByIdOrThrow(id);
		Set<Long> authorIds = book.getAuthorIds();

		if (authorIds.isEmpty()) {
			deleteBook(id);
			//?
			throw new ResourceNotFoundException("The book was deleted because had no authors");
			}

		Set<AuthorEntity> authors = Set.copyOf(authorRepository.findAllById(authorIds));
		bookEntity.setAuthors(authors);

		bookMapper.update(bookEntity, book);

		return bookMapper.mapBookEntityToBookDto(bookEntity);
	}

	@Transactional
	public void deleteBook(Long id) {
		BookEntity bookEntity = findByIdOrThrow(id);
		repository.deleteById(bookEntity.getId());
	}

	private BookEntity findByIdOrThrow(Long id) {
		return repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("There is no book with id: " + id));
	}
}
