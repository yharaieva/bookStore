package com.haraieva.bookStore.mapper;

import com.haraieva.bookStore.dto.BookChangeDto;
import com.haraieva.bookStore.dto.BookChangeDtoWithAuthor;
import com.haraieva.bookStore.dto.BookDto;
import com.haraieva.bookStore.entity.BookEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookMapper {

	BookDto mapBookEntityToBookDto(BookEntity bookEntity);
	BookEntity mapBookChangeDtoWithAuthorToBookEntity(BookChangeDtoWithAuthor book);
	BookChangeDtoWithAuthor mapBookChangeDtoToBookChangeDtoWithAuthor(BookChangeDto book);
}
