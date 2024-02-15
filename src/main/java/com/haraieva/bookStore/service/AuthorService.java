package com.haraieva.bookStore.service;

import com.haraieva.bookStore.dto.AuthorDto;
import com.haraieva.bookStore.entity.AuthorEntity;
import com.haraieva.bookStore.mapper.AuthorMapper;
import com.haraieva.bookStore.repository.AuthorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthorService {

	private final AuthorRepository repository;
	private final AuthorMapper mapper;

	public AuthorEntity findByLastnameAndFirstnameOrCreate(AuthorDto authorDto) {
		Optional<AuthorEntity> optionalAuthor = repository.findByFirstnameAndLastname(authorDto.getFirstName(), authorDto.getLastName());
		if (optionalAuthor.isEmpty()) {
			return repository.save(mapper.mapAuthorDtoToAuthorEntity(authorDto));
		}
		return optionalAuthor.get();
	}
}
