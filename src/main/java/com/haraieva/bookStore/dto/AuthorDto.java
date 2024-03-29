package com.haraieva.bookStore.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class AuthorDto {
	private Long id;
	private String firstName;
	private String lastName;
}
