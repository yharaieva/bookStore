package com.haraieva.bookStore.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BookDto {
	private Long id;
	private String title;
	private String author;
}
