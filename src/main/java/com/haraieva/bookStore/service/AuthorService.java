package com.haraieva.bookStore.service;

import com.haraieva.bookStore.dto.AuthorDto;
import com.haraieva.bookStore.entity.AuthorEntity;
import com.haraieva.bookStore.exceptions.ResourceNotFoundException;
import com.haraieva.bookStore.mapper.AuthorMapper;
import com.haraieva.bookStore.repository.AuthorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AuthorService {

	private final AuthorRepository repository;
	private final AuthorMapper mapper;

	public List<AuthorDto> getAuthors() {
		return repository.findAll().stream()
				.map(mapper::mapAuthorEntityToAuthorDto)
				.collect(Collectors.toList());
	}

	public AuthorEntity findByLastnameAndFirstname(AuthorDto authorDto) {
		return repository.findByFirstnameAndLastname(authorDto.getFirstName(), authorDto.getLastName())
				.orElseThrow(() -> new ResourceNotFoundException("There is no author : " + authorDto.getFirstName() + " " + authorDto.getLastName()));
	}

	public AuthorEntity findByLastnameAndFirstnameOrCreate(AuthorDto authorDto) {
		Optional<AuthorEntity> optionalAuthor = repository.findByFirstnameAndLastname(authorDto.getFirstName(), authorDto.getLastName());
		if (optionalAuthor.isEmpty()) {
			return repository.save(mapper.mapAuthorDtoToAuthorEntity(authorDto));
		}
		return optionalAuthor.get();
	}
}
