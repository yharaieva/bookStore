package com.haraieva.bookStore.repository;

import com.haraieva.bookStore.entity.AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<AuthorEntity, Long> {}
