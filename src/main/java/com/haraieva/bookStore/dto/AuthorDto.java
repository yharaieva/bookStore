package com.haraieva.bookStore.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class AuthorDto {
	@NotBlank
	private String firstName;
	@NotBlank
	private String lastName;
}
