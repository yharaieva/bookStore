package com.haraieva.bookStore.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Set;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class BookDto {
	private Long id;
	private String title;
	private Set<AuthorDto> authors;
}
