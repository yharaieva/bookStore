package com.haraieva.bookStore.dto;

import com.haraieva.bookStore.entity.AuthorEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BookChangeDtoWithAuthor {
	private String title;
	private AuthorEntity author;

	public void setAuthor(AuthorEntity author) {
		this.author = author;
	}
}
