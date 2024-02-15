package com.haraieva.bookStore.service;

import com.haraieva.bookStore.dto.BookChangeDto;
import com.haraieva.bookStore.dto.BookChangeDtoWithAuthor;
import com.haraieva.bookStore.dto.BookDto;
import com.haraieva.bookStore.entity.AuthorEntity;
import com.haraieva.bookStore.entity.BookEntity;
import com.haraieva.bookStore.exceptions.ResourceNotFoundException;
import com.haraieva.bookStore.mapper.BookMapper;
import com.haraieva.bookStore.repository.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BookService {

	private final AuthorService authorService;
	private final BookRepository repository;
	private final BookMapper mapper;

	public List<BookDto> getBooks(Integer pageNo, Integer pageSize, String sortBy) {
		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

		Page<BookEntity> pagedResult = repository.findAll(paging);

		if (pagedResult.hasContent()) {
			return pagedResult.getContent().stream()
					.map(mapper::mapBookEntityToBookDto)
					.collect(Collectors.toList());
		} else {
			return new ArrayList<>();
		}
	}

	public BookDto getBookById(long bookId) {
		BookEntity bookEntity = findByIdOrThrow(bookId);

		return mapper.mapBookEntityToBookDto(bookEntity);
	}

	@Transactional
	public BookDto addBook(BookChangeDto book) {
		AuthorEntity authorEntity = authorService.findByLastnameAndFirstnameOrCreate(book.getAuthor());

		BookChangeDtoWithAuthor bookWithAuthor = mapper.mapBookChangeDtoToBookChangeDtoWithAuthor(book);
		bookWithAuthor.setAuthor(authorEntity);
		BookEntity bookEntity = mapper.mapBookChangeDtoWithAuthorToBookEntity(bookWithAuthor);

		repository.save(bookEntity);

		return mapper.mapBookEntityToBookDto(bookEntity);
	}

	@Transactional
	public BookDto updateBook(Long id, BookChangeDto book) {
		BookEntity bookEntity = findByIdOrThrow(id);
		AuthorEntity authorEntity = authorService.findByLastnameAndFirstnameOrCreate(book.getAuthor());

//		bookEntity = mapper.mapBookChangeDtoToBookEntity(bookDto);

		bookEntity.setTitle(book.getTitle());
		bookEntity.setAuthor(authorEntity);

		return mapper.mapBookEntityToBookDto(bookEntity);
	}

	private BookEntity findByIdOrThrow(Long id) {
		return repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("There is no book with id: " + id));
	}
}
