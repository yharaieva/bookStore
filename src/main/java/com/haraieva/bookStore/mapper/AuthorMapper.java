package com.haraieva.bookStore.mapper;

import com.haraieva.bookStore.dto.AuthorDto;
import com.haraieva.bookStore.entity.AuthorEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

	AuthorDto mapAuthorEntityToAuthorDto(AuthorEntity authorEntity);
	AuthorEntity mapAuthorDtoToAuthorEntity(AuthorDto authorDto);

}
