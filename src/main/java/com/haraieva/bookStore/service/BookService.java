package com.haraieva.bookStore.service;

import com.haraieva.bookStore.dto.AuthorDto;
import com.haraieva.bookStore.dto.BookChangeDto;
import com.haraieva.bookStore.dto.BookDto;
import com.haraieva.bookStore.entity.AuthorEntity;
import com.haraieva.bookStore.entity.BookEntity;
import com.haraieva.bookStore.exceptions.ResourceNotFoundException;
import com.haraieva.bookStore.fileStorage.FileStorage;
import com.haraieva.bookStore.mapper.AuthorMapper;
import com.haraieva.bookStore.mapper.BookMapper;
import com.haraieva.bookStore.repository.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class BookService {

	private final AuthorService authorService;
	private final BookRepository repository;
	private final BookMapper bookMapper;
	private final AuthorMapper authorMapper;
	private final FileStorage fileStorage;

	public Page<BookDto> getBooks(Integer pageNo, Integer pageSize, String sortBy) {
		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

		Page<BookEntity> pagedResult = repository.findAll(paging);

		if (pagedResult.hasContent()) {
			return new PageImpl<>(
					pagedResult.getContent().stream()
					.map(bookMapper::mapBookEntityToBookDto)
					.collect(Collectors.toList())
			);
		} else {
			return new PageImpl<>(new ArrayList<>());
		}
	}

	public BookDto getBookById(long bookId) {
		BookEntity bookEntity = findByIdOrThrow(bookId);

		return bookMapper.mapBookEntityToBookDto(bookEntity);
	}

	@Transactional
	public BookDto addBook(BookChangeDto book) {
		BookEntity bookEntity = bookMapper.mapBookChangeDtoToBookEntity(book);

		Stream<AuthorDto> authorDtos = book.getAuthorIds().stream()
				.map(authorService::findById);

		Set<AuthorEntity> authors = authorDtos
				.map(authorMapper::mapAuthorDtoToAuthorEntity)
				.collect(Collectors.toSet());

		bookEntity.setAuthors(authors);

		repository.save(bookEntity);

		return bookMapper.mapBookEntityToBookDto(bookEntity);
	}

	@Transactional
	public BookDto updateBook(Long id, BookChangeDto book) {
		BookEntity bookEntity = findByIdOrThrow(id);
		bookMapper.update(bookEntity, book);

		Stream<AuthorDto> authorDtos = book.getAuthorIds().stream()
				.map(authorService::findById);

		Set<AuthorEntity> authors = authorDtos
				.map(authorMapper::mapAuthorDtoToAuthorEntity)
				.collect(Collectors.toSet());

		bookEntity.setAuthors(authors);

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
