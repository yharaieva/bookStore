package com.haraieva.bookStore.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "books")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Book {

	@Id
	@Column(name = "id", nullable = false, unique = true)
	private int id;

	@Column(name = "title", nullable = false, length = 1000, unique = false)
	private String title;

}
