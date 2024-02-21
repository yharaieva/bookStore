package com.haraieva.bookStore.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BookChangeDto {
	@NotBlank
	private String title;
	@Valid
	private AuthorDto author;
}
