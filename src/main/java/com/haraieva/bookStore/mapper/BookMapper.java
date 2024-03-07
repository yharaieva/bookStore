package com.haraieva.bookStore.mapper;

import com.haraieva.bookStore.dto.BookChangeDto;
import com.haraieva.bookStore.dto.BookDto;
import com.haraieva.bookStore.entity.AuthorEntity;
import com.haraieva.bookStore.entity.BookEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BookMapper {

	BookDto mapBookEntityToBookDto(BookEntity bookEntity);
	BookEntity mapBookChangeDtoToBookEntity(BookChangeDto book);
	void update(@MappingTarget BookEntity bookEntity, BookChangeDto bookChangeDto);
}
