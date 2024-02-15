package com.haraieva.bookStore.repository;

import com.haraieva.bookStore.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<BookEntity, Long> {}
