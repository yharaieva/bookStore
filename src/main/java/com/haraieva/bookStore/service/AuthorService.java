package com.haraieva.bookStore.service;

import com.haraieva.bookStore.dto.AuthorChangeDto;
import com.haraieva.bookStore.dto.AuthorDto;
import com.haraieva.bookStore.entity.AuthorEntity;
import com.haraieva.bookStore.entity.BookEntity;
import com.haraieva.bookStore.exceptions.ResourceNotFoundException;
import com.haraieva.bookStore.mapper.AuthorMapper;
import com.haraieva.bookStore.repository.AuthorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class AuthorService {

	private final AuthorRepository repository;
	private final AuthorMapper mapper;

	public AuthorDto findById(long id) {
		AuthorEntity authorEntity = findByIdOrThrow(id);

		return mapper.mapAuthorEntityToAuthorDto(authorEntity);
	}

	@Transactional
	public AuthorDto addAuthor(AuthorChangeDto authorChangeDto) {
		AuthorEntity authorEntity = repository.save(mapper.mapAuthorChangeDtoToAuthorEntity(authorChangeDto));

		return mapper.mapAuthorEntityToAuthorDto(authorEntity);
	}

	@Transactional
	public void deleteAuthor(Long id) {
		AuthorEntity authorEntity = findByIdOrThrow(id);
		repository.deleteById(authorEntity.getId());
	}

	private AuthorEntity findByIdOrThrow(Long id) {
		return repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("There is no author with id: " + id));
	}

//	@Transactional
//	public AuthorEntity findByLastnameAndFirstnameOrThrow(AuthorChangeDto authorChangeDto) {
//		Optional<AuthorEntity> optionalAuthor = repository.findByLastNameAndFirstName(authorChangeDto.getLastName(), authorChangeDto.getFirstName());
//		if (optionalAuthor.isEmpty()) {
//			throw new ResourceNotFoundException(String.format("There is no author with the name %1$s %2$s",
//					authorChangeDto.getFirstName(), authorChangeDto.getLastName()));
//		}
//		return optionalAuthor.get();
//	}
}
